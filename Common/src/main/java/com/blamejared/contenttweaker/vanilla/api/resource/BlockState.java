package com.blamejared.contenttweaker.vanilla.api.resource;

import com.blamejared.contenttweaker.core.api.resource.ResourceSerializer;
import com.blamejared.contenttweaker.core.api.resource.StandardResourceSerializers;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class BlockState {
    @FunctionalInterface
    private interface ModelVerifier {
        void checkModel(final ResourceLocation name, final int x, final int y, final boolean uvLock);
    }

    private record ModelData(ResourceLocation name, int x, int y, boolean uvLock) {
        JsonElement serialize() {
            final JsonObject object = new JsonObject();
            object.addProperty("model", this.name().toString());
            object.addProperty("x", this.x());
            object.addProperty("y", this.y());
            object.addProperty("uvlock", this.uvLock());
            return object;
        }
    }

    private record WeightedModelData(ModelData data, int weight) {
        JsonElement serialize() {
            final JsonObject object = this.data().serialize().getAsJsonObject();
            object.addProperty("weight", this.weight());
            return object;
        }
    }

    private record SingleOrWeightedData(ModelData single, List<WeightedModelData> multiple) {
        JsonElement serialize() {
            if (this.single() != null) {
                return this.single().serialize();
            }
            final JsonArray array = new JsonArray();
            this.multiple().stream().map(WeightedModelData::serialize).forEach(array::add);
            return array;
        }
    }

    private record VariantModel(Map<String, VariantData> variants) {
        private record VariantData(SingleOrWeightedData model) {
            JsonElement serialize() {
                return this.model().serialize();
            }
        }

        JsonElement serialize() {
            final JsonObject main = new JsonObject();
            final JsonObject object = new JsonObject();
            this.variants().forEach((name, data) -> object.add(name, data.serialize()));
            main.add("variants", object);
            return main;
        }
    }

    private record MultipartModel(List<MultipartSection> sections) {
        private record MultipartSection(List<MultipartCase> when, List<MultipartOrCase> or, SingleOrWeightedData apply) {
            private record MultipartCase(String name, String value) {}

            private record MultipartOrCase(List<MultipartCase> cases) {
                JsonElement serialize() {
                    final JsonObject object = new JsonObject();
                    this.cases().forEach(it -> object.addProperty(it.name(), it.value()));
                    return object;
                }
            }

            JsonElement serialize() {
                final JsonObject section = new JsonObject();
                final JsonObject when = new JsonObject();

                if (!this.or().isEmpty()) {
                    final JsonArray or = new JsonArray();
                    this.or().stream().map(MultipartOrCase::serialize).forEach(or::add);
                    when.add("OR", or);
                } else if (!this.when().isEmpty()) {
                    this.when().forEach(it -> when.addProperty(it.name(), it.value()));
                }

                if (!when.entrySet().isEmpty()) {
                    section.add("when", when);
                }

                section.add("apply", this.apply().serialize());
                return section;
            }
        }

        JsonElement serialize() {
            final JsonObject main = new JsonObject();
            final JsonArray array = new JsonArray();
            this.sections().stream().map(MultipartSection::serialize).forEach(array::add);
            main.add("multiparts", array);
            return main;
        }
    }

    public static final class WeightedModelBuilder<T, U extends WeightedModelBuilder.Parent<T>> {
        @FunctionalInterface
        interface Parent<J> {
            J variant(final String name, final SingleOrWeightedData data);
        }

        private final U parent;
        private final String name;
        private final ModelVerifier verifier;
        private final List<WeightedModelData> models;

        WeightedModelBuilder(final U parent, final String name, final ModelVerifier verifier) {
            this.parent = parent;
            this.name = name;
            this.verifier = verifier;
            this.models = new ArrayList<>();
        }

        public WeightedModelBuilder<T, U> add(final ResourceLocation name, final int weight) {
            return this.add(name, 0, 0, weight);
        }

        public WeightedModelBuilder<T, U> add(final ResourceLocation name, final int x, final int y, final int weight) {
            return this.add(name, x, y, false, weight);
        }

        public WeightedModelBuilder<T, U> add(final ResourceLocation name, final int x, final int y, final boolean uvLock, final int weight) {
            this.verifier.checkModel(name, x, y, uvLock);
            if (weight <= 0) {
                throw new IllegalArgumentException("Invalid weight " + weight);
            }
            this.models.add(new WeightedModelData(new ModelData(name, x, y, uvLock), weight));
            return this;
        }

        public T finish() {
            if (this.models.isEmpty()) {
                throw new IllegalStateException("No model specified for variant " + this.name);
            }
            return this.parent.variant(this.name, new SingleOrWeightedData(null, new ArrayList<>(this.models)));
        }
    }

    public static final class MultipartOrSectionBuilder {
        private final MultipartSectionBuilder parent;
        private final List<MultipartModel.MultipartSection.MultipartCase> conditions;

        MultipartOrSectionBuilder(final MultipartSectionBuilder parent) {
            this.parent = parent;
            this.conditions = new ArrayList<>();
        }

        public MultipartOrSectionBuilder when(final String name, final String value) {
            this.conditions.add(new MultipartModel.MultipartSection.MultipartCase(Objects.requireNonNull(name), Objects.requireNonNull(value)));
            return this;
        }

        public MultipartSectionBuilder finish() {
            return this.parent.or(new MultipartModel.MultipartSection.MultipartOrCase(new ArrayList<>(this.conditions)));
        }
    }

    public static final class MultipartSectionBuilder {
        private record ParentAdapter(MultipartSectionBuilder parent) implements WeightedModelBuilder.Parent<MultipartSectionBuilder> {
            @Override
            public MultipartSectionBuilder variant(final String name, final SingleOrWeightedData data) {
                return this.parent().variant(data);
            }
        }

        private final MultipartModelBuilder parent;
        private final ModelVerifier verifier;
        private final List<MultipartModel.MultipartSection.MultipartCase> cases;
        private final List<MultipartModel.MultipartSection.MultipartOrCase> or;
        private SingleOrWeightedData data;

        MultipartSectionBuilder(final MultipartModelBuilder parent, final ModelVerifier verifier) {
            this.parent = parent;
            this.verifier = verifier;
            this.cases = new ArrayList<>();
            this.or = new ArrayList<>();
            this.data = null;
        }

        public MultipartSectionBuilder when(final String name, final String value) {
            if (!this.or.isEmpty()) {
                throw new IllegalArgumentException("Cannot set 'when' case when 'or' is present");
            }
            this.cases.add(new MultipartModel.MultipartSection.MultipartCase(Objects.requireNonNull(name), Objects.requireNonNull(value)));
            return this;
        }

        public MultipartOrSectionBuilder or() {
            if (!this.cases.isEmpty()) {
                throw new IllegalArgumentException("Cannot set 'or' cases when 'when' is present");
            }
            return new MultipartOrSectionBuilder(this);
        }

        public WeightedModelBuilder<MultipartSectionBuilder, ParentAdapter> apply() {
            if (this.data != null) {
                throw new IllegalArgumentException("Already set model for multipart case");
            }
            return new WeightedModelBuilder<>(new ParentAdapter(this), "<multipart>", this.verifier);
        }

        public MultipartModelBuilder finish() {
            if (!this.cases.isEmpty() && !this.or.isEmpty()) {
                throw new IllegalArgumentException("Both 'when' and 'or' specified");
            }
            return this.parent.section(new MultipartModel.MultipartSection(new ArrayList<>(this.cases), new ArrayList<>(this.or), this.data));
        }

        MultipartSectionBuilder or(final MultipartModel.MultipartSection.MultipartOrCase or) {
            if (!this.cases.isEmpty()) {
                throw new IllegalArgumentException("Cannot set 'or' cases when 'when' is present");
            }
            this.or.add(or);
            return this;
        }

        MultipartSectionBuilder variant(final SingleOrWeightedData data) {
            this.data = data;
            return this;
        }
    }

    public static final class VariantModelBuilder {
        private record ParentAdapter(VariantModelBuilder parent) implements WeightedModelBuilder.Parent<VariantModelBuilder> {
            @Override
            public VariantModelBuilder variant(final String name, final SingleOrWeightedData data) {
                return this.parent().variant(name, data);
            }
        }

        private final BlockState parent;
        private final ModelVerifier verifier;
        private final Map<String, VariantModel.VariantData> variants;

        VariantModelBuilder(final BlockState parent, final ModelVerifier verifier) {
            this.parent = parent;
            this.verifier = verifier;
            this.variants = new LinkedHashMap<>();
        }

        public VariantModelBuilder singleModelFor(final String variant, final ResourceLocation name) {
            return this.singleModelFor(variant, name, 0, 0);
        }

        public VariantModelBuilder singleModelFor(final String variant, final ResourceLocation name, final int x, final int y) {
            return this.singleModelFor(variant, name, x, y, false);
        }

        public VariantModelBuilder singleModelFor(final String variant, final ResourceLocation name, final int x, final int y, final boolean uvLock) {
            this.checkVariant(variant);
            this.verifier.checkModel(name, x, y, uvLock);
            this.variants.put(variant, new VariantModel.VariantData(new SingleOrWeightedData(new ModelData(name, x, y, uvLock), null)));
            return this;
        }

        public WeightedModelBuilder<VariantModelBuilder, VariantModelBuilder.ParentAdapter> weightedModelFor(final String variant) {
            this.checkVariant(variant);
            return new WeightedModelBuilder<>(new ParentAdapter(this), variant, this.verifier);
        }

        public BlockState finish() {
            return this.parent.variant(new VariantModel(new LinkedHashMap<>(this.variants)));
        }

        VariantModelBuilder variant(final String name, final SingleOrWeightedData data) {
            this.checkVariant(name);
            this.variants.put(name, new VariantModel.VariantData(data));
            return this;
        }

        private void checkVariant(final String name) {
            if (this.variants.containsKey(Objects.requireNonNull(name))) {
                throw new IllegalArgumentException("Already created data for variant " + name);
            }
        }
    }

    public static final class MultipartModelBuilder {
        private final BlockState parent;
        private final ModelVerifier verifier;
        private final List<MultipartModel.MultipartSection> sections;

        MultipartModelBuilder(final BlockState parent, final ModelVerifier verifier) {
            this.parent = parent;
            this.verifier = verifier;
            this.sections = new ArrayList<>();
        }

        public MultipartSectionBuilder section() {
            return new MultipartSectionBuilder(this, this.verifier);
        }

        public BlockState finish() {
            if (this.sections.isEmpty()) {
                throw new IllegalArgumentException("No sections specified");
            }
            return this.parent.multipart(new MultipartModel(new ArrayList<>(this.sections)));
        }

        MultipartModelBuilder section(final MultipartModel.MultipartSection section) {
            this.sections.add(section);
            return this;
        }
    }

    private record VariantOrMultipart(VariantModel variant, MultipartModel multipart) {
        JsonElement serialize() {
            return this.variant() != null? this.variant().serialize() : this.multipart().serialize();
        }
    }

    public static final ResourceSerializer<BlockState> SERIALIZER = BlockState::serialize;

    private VariantOrMultipart state;

    private BlockState() {
        this.state = null;
    }

    public static VariantModelBuilder variant() {
        return new VariantModelBuilder(new BlockState(), BlockState::checkModel);
    }

    public static MultipartModelBuilder multipart() {
        return new MultipartModelBuilder(new BlockState(), BlockState::checkModel);
    }

    private static void checkModel(final ResourceLocation name, final int x, final int y, final boolean uvLock) {
        Objects.requireNonNull(name);
        if ((x % 90) != 0 || (y % 90) != 0) {
            throw new IllegalStateException("Invalid angles specified (%d,%d): must be a multiple of 90".formatted(x, y));
        }
    }

    BlockState variant(final VariantModel data) {
        this.state = new VariantOrMultipart(data, null);
        return this;
    }

    BlockState multipart(final MultipartModel data) {
        this.state = new VariantOrMultipart(null, data);
        return this;
    }

    private byte[] serialize() {
        return StandardResourceSerializers.JSON.serialize(this.state.serialize());
    }
}
