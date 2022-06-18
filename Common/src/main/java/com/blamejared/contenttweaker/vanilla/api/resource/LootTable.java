package com.blamejared.contenttweaker.vanilla.api.resource;

import com.blamejared.contenttweaker.core.api.resource.ResourceSerializer;
import com.blamejared.contenttweaker.core.api.resource.StandardResourceSerializers;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class LootTable {
    // TODO("Better data for functions and conditions, then again...")
    private interface TypeParametersSerializable {
        ResourceLocation type();
        JsonObject parameters();

        default String typeId() {
            return this instanceof LootFunction? "function" : this instanceof LootCondition? "condition" : "type";
        }

        default JsonElement serialize() {
            final JsonObject object = new JsonObject();
            object.addProperty(this.typeId(), this.type().toString());
            this.parameters().entrySet().forEach(entry -> object.add(entry.getKey(), entry.getValue()));
            return object;
        }
    }

    private record LootFunction(ResourceLocation type, JsonObject parameters) implements TypeParametersSerializable {}

    private record LootCondition(ResourceLocation type, JsonObject parameters) implements TypeParametersSerializable {}

    private record LootNumber(Integer i, Float f, ResourceLocation type, JsonObject parameters) implements TypeParametersSerializable {
        @Override
        public JsonElement serialize() {
            if (this.i() != null || this.f() != null) {
                return new JsonPrimitive(this.i() == null? this.f() : this.i());
            }
            return TypeParametersSerializable.super.serialize();
        }
    }

    private interface ConditionFunctionSerializable {
        List<LootCondition> conditions();
        List<LootFunction> functions();

        default void serializeInto(final JsonObject object) {
            if (!this.conditions().isEmpty()) {
                final JsonArray conditions = new JsonArray();
                this.conditions().stream().map(LootCondition::serialize).forEach(conditions::add);
                object.add("conditions", conditions);
            }
            if (!this.functions().isEmpty()) {
                final JsonArray functions = new JsonArray();
                this.functions().stream().map(LootFunction::serialize).forEach(functions::add);
                object.add("functions", functions);
            }
        }
    }


    private record LootFlags(Boolean expand, Integer weight, Integer quality) {
        void serializeInto(final JsonObject object) {
            if (this.expand() != null) {
                object.addProperty("expand", this.expand());
            }
            if (this.weight() != null) {
                object.addProperty("weight", this.weight());
            }
            if (this.quality() != null) {
                object.addProperty("quality", this.quality());
            }
        }
    }

    private record LootEntry(List<LootCondition> conditions, List<LootFunction> functions, ResourceLocation type, String name, List<LootEntry> children, LootFlags flags)
            implements ConditionFunctionSerializable {
        JsonElement serialize() {
            final JsonObject object = new JsonObject();
            this.serializeInto(object);
            object.addProperty("type", this.type().toString());
            if (this.name != null) {
                object.addProperty("name", this.name());
            }
            if (!this.children().isEmpty()) {
                final JsonArray children = new JsonArray();
                this.children().stream().map(LootEntry::serialize).forEach(children::add);
                object.add("children", children);
            }
            this.flags().serializeInto(object);
            return object;
        }
    }

    private record LootPool(List<LootCondition> conditions, List<LootFunction> functions, LootNumber rolls, LootNumber bonus, List<LootEntry> entries)
            implements ConditionFunctionSerializable {
        JsonElement serialize() {
            final JsonObject object = new JsonObject();
            this.serializeInto(object);
            object.add("rolls", this.rolls().serialize());
            object.add("bonus_rolls", this.bonus().serialize());
            if (!this.entries().isEmpty()) {
                final JsonArray children = new JsonArray();
                this.entries().stream().map(LootEntry::serialize).forEach(children::add);
                object.add("entries", children);
            }
            return object;
        }
    }

    public static final class LootEntryBuilder<T, U extends LootEntryBuilder.Parent<T>> {
        @FunctionalInterface
        interface Parent<J> {
            J entry(final LootEntry entry);
        }

        private record ParentAdapter<S, R extends Parent<S>>(LootEntryBuilder<S, R> parent) implements Parent<LootEntryBuilder<S, R>> {
            @Override
            public LootEntryBuilder<S, R> entry(final LootEntry entry) {
                return this.parent().entry(entry);
            }
        }

        private record ValidityFlags(boolean name, boolean children, boolean expand) {
            private static final Set<ResourceLocation> VALID_FOR_NAME = s("item", "tag", "loot_table", "dynamic");
            private static final Set<ResourceLocation> VALID_FOR_CHILDREN = s("group", "alternatives", "sequence");
            private static final Set<ResourceLocation> VALID_FOR_EXPAND = s("tag");
            private static final Set<ResourceLocation> VALID_TYPES = s(VALID_FOR_NAME, VALID_FOR_CHILDREN, "empty");

            static ValidityFlags of(final ResourceLocation type) {
                if (!VALID_TYPES.contains(Objects.requireNonNull(type))) {
                    throw new IllegalArgumentException("Invalid type " + type);
                }
                return new ValidityFlags(VALID_FOR_NAME.contains(type), VALID_FOR_CHILDREN.contains(type), VALID_FOR_EXPAND.contains(type));
            }

            private static Set<ResourceLocation> s(final String... elements) {
                return Arrays.stream(elements).map(ResourceLocation::new).collect(Collectors.toUnmodifiableSet());
            }

            @SuppressWarnings("SameParameterValue")
            private static Set<ResourceLocation> s(final Set<ResourceLocation> a, final Set<ResourceLocation> b, final String... c) {
                return Stream.concat(Stream.concat(a.stream(), b.stream()), Arrays.stream(c).map(ResourceLocation::new)).collect(Collectors.toUnmodifiableSet());
            }
        }

        private final U parent;
        private final ResourceLocation type;
        private final ValidityFlags validityFlags;
        private final List<LootCondition> conditions;
        private final List<LootFunction> functions;
        private final List<LootEntry> children;
        private String name;
        private LootFlags flags;

        LootEntryBuilder(final U parent, final ResourceLocation type) {
            this.parent = parent;
            this.type = type;
            this.validityFlags = ValidityFlags.of(this.type);
            this.conditions = new ArrayList<>();
            this.functions = new ArrayList<>();
            this.children = new ArrayList<>();
            this.name = null;
            this.flags = new LootFlags(null, null, null);
        }

        public LootEntryBuilder<T, U> name(final String name) {
            if (!this.validityFlags.name()) {
                throw new IllegalStateException("Unable to set name with type " + this.type);
            }
            this.name = Objects.requireNonNull(name);
            return this;
        }

        public LootEntryBuilder<T, U> conditionally(final ResourceLocation type, final JsonObject parameters) {
            this.conditions.add(new LootCondition(Objects.requireNonNull(type), Objects.requireNonNull(parameters)));
            return this;
        }

        public LootEntryBuilder<T, U> function(final ResourceLocation type, final JsonObject parameters) {
            this.functions.add(new LootFunction(Objects.requireNonNull(type), Objects.requireNonNull(parameters)));
            return this;
        }

        public LootEntryBuilder<LootEntryBuilder<T, U>, ParentAdapter<T, U>> child(final ResourceLocation type) {
            if (!this.validityFlags.children()) {
                throw new IllegalStateException("Unable to set add children with type " + this.type);
            }
            return new LootEntryBuilder<>(new ParentAdapter<>(this), type);
        }

        public LootEntryBuilder<T, U> expand(final boolean expand) {
            if (!this.validityFlags.expand()) {
                throw new IllegalStateException("Unable to set expand for type " + this.type);
            }
            this.flags = new LootFlags(expand, this.flags.weight(), this.flags.quality());
            return this;
        }

        public LootEntryBuilder<T, U> expand() {
            return this.expand(true);
        }

        public LootEntryBuilder<T, U> weight(final int weight) {
            this.flags = new LootFlags(this.flags.expand(), weight, this.flags.quality());
            return this;
        }

        public LootEntryBuilder<T, U> quality(final int quality) {
            this.flags = new LootFlags(this.flags.expand(), this.flags.weight(), quality);
            return this;
        }

        public T finish() {
            return this.parent.entry(new LootEntry(new ArrayList<>(this.conditions), new ArrayList<>(this.functions), this.type, this.name, new ArrayList<>(this.children), this.flags));
        }

        LootEntryBuilder<T, U> entry(final LootEntry entry) {
            this.children.add(entry);
            return this;
        }
    }

    public static final class LootPoolBuilder {
        private record ParentAdapter(LootPoolBuilder builder) implements LootEntryBuilder.Parent<LootPoolBuilder> {
            @Override
            public LootPoolBuilder entry(final LootEntry entry) {
                return this.builder().entry(entry);
            }
        }

        private final LootTable parent;
        private final List<LootCondition> conditions;
        private final List<LootFunction> functions;
        private final List<LootEntry> entries;
        private LootNumber rolls;
        private LootNumber bonus;

        LootPoolBuilder(final LootTable parent) {
            this.parent = parent;
            this.conditions = new ArrayList<>();
            this.functions = new ArrayList<>();
            this.entries = new ArrayList<>();
            this.rolls = null;
            this.bonus = null;
        }

        public LootPoolBuilder conditionally(final ResourceLocation type, final JsonObject parameters) {
            this.conditions.add(new LootCondition(Objects.requireNonNull(type), Objects.requireNonNull(parameters)));
            return this;
        }

        public LootPoolBuilder function(final ResourceLocation type, final JsonObject parameters) {
            this.functions.add(new LootFunction(Objects.requireNonNull(type), Objects.requireNonNull(parameters)));
            return this;
        }

        public LootPoolBuilder rolls(final int rolls) {
            if (this.rolls != null) {
                throw new IllegalStateException("Already specified rolls");
            }
            if (rolls < 0) {
                throw new IllegalArgumentException("Rolls must be positive");
            }
            this.rolls = new LootNumber(rolls, null, null, null);
            return this;
        }

        public LootPoolBuilder rolls(final ResourceLocation type, final JsonObject parameters) {
            if (this.rolls != null) {
                throw new IllegalStateException("Already specified rolls");
            }
            this.rolls = new LootNumber(null, null, Objects.requireNonNull(type), Objects.requireNonNull(parameters));
            return this;
        }

        public LootPoolBuilder bonusRolls(final float rolls) {
            if (this.bonus != null) {
                throw new IllegalStateException("Already specified rolls");
            }
            if (rolls < 0) {
                throw new IllegalArgumentException("Rolls must be positive");
            }
            this.bonus = new LootNumber(null, rolls, null, null);
            return this;
        }

        public LootPoolBuilder bonusRolls(final ResourceLocation type, final JsonObject parameters) {
            if (this.bonus != null) {
                throw new IllegalStateException("Already specified rolls");
            }
            this.bonus = new LootNumber(null, null, Objects.requireNonNull(type), Objects.requireNonNull(parameters));
            return this;
        }

        public LootEntryBuilder<LootPoolBuilder, ParentAdapter> entry(final ResourceLocation type) {
            return new LootEntryBuilder<>(new ParentAdapter(this), Objects.requireNonNull(type));
        }

        public LootTable finish() {
            return this.parent.pool(new LootPool(new ArrayList<>(this.conditions), new ArrayList<>(this.functions), this.rolls, this.bonus, new ArrayList<>(this.entries)));
        }

        LootPoolBuilder entry(final LootEntry entry) {
            this.entries.add(entry);
            return this;
        }
    }

    public static final ResourceSerializer<LootTable> SERIALIZER = LootTable::serialize;

    private final ResourceLocation type;
    private final List<LootFunction> functions;
    private final List<LootPool> pools;

    private LootTable(final ResourceLocation type) {
        this.type = type;
        this.functions = new ArrayList<>();
        this.pools = new ArrayList<>();
    }

    public static LootTable of(final ResourceLocation type) {
        return new LootTable(Objects.requireNonNull(type));
    }

    public static LootTable ofBlock() {
        return new LootTable(new ResourceLocation("block"));
    }

    public static LootTable ofChest() {
        return new LootTable(new ResourceLocation("chest"));
    }

    public static LootTable ofEmpty() {
        return new LootTable(null);
    }

    public static LootTable ofEntity() {
        return new LootTable(new ResourceLocation("entity"));
    }

    public static LootTable ofFishing() {
        return new LootTable(new ResourceLocation("fishing"));
    }

    public static LootTable ofGeneric() {
        return new LootTable(new ResourceLocation("generic"));
    }

    public LootTable function(final ResourceLocation type, final JsonObject parameters) {
        this.functions.add(new LootFunction(Objects.requireNonNull(type), Objects.requireNonNull(parameters)));
        return this;
    }

    public LootPoolBuilder pool() {
        return new LootPoolBuilder(this);
    }

    LootTable pool(final LootPool pool) {
        this.pools.add(pool);
        return this;
    }

    private byte[] serialize() {
        final JsonObject object = new JsonObject();
        if (this.type != null) {
            object.addProperty("type", this.type.toString());
        }
        if (!this.functions.isEmpty()) {
            final JsonArray functions = new JsonArray();
            this.functions.stream().map(LootFunction::serialize).forEach(functions::add);
            object.add("functions", functions);
        }
        if (!this.pools.isEmpty()) {
            final JsonArray pools = new JsonArray();
            this.pools.stream().map(LootPool::serialize).forEach(pools::add);
            object.add("pools", pools);
        }
        return StandardResourceSerializers.JSON.serialize(object);
    }
}
