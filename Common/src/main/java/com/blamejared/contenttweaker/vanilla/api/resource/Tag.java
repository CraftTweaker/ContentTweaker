package com.blamejared.contenttweaker.vanilla.api.resource;

import com.blamejared.contenttweaker.core.api.resource.ResourceSerializer;
import com.blamejared.contenttweaker.core.api.resource.StandardResourceSerializers;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.resources.ResourceLocation;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class Tag {
    private record Entry(ResourceLocation location, Type type) {
        enum Type {
            OBJECT,
            TAG
        }

        static Entry object(final ResourceLocation location) {
            return new Entry(Objects.requireNonNull(location), Type.OBJECT);
        }

        static Entry tag(final ResourceLocation location) {
            return new Entry(Objects.requireNonNull(location), Type.TAG);
        }

        JsonElement serialize() {
            return new JsonPrimitive("%s%s".formatted(this.type() == Type.TAG? "#" : "", this.location()));
        }
    }

    public static final ResourceSerializer<Tag> SERIALIZER = Tag::serialize;

    private final Set<Entry> entries;

    private Tag() {
        this.entries = new HashSet<>();
    }

    public static Tag of() {
        return new Tag();
    }

    public Tag addObject(final ResourceLocation location) {
        return this.add(Entry.object(location));
    }

    public Tag addTag(final ResourceLocation location) {
        return this.add(Entry.tag(location));
    }

    private Tag add(final Entry entry) {
        this.entries.add(entry);
        return this;
    }

    private byte[] serialize() {
        final JsonArray values = new JsonArray();
        this.entries.stream().map(Entry::serialize).forEach(values::add);
        final JsonObject object = new JsonObject();
        object.add("values", values);
        object.add("replace", new JsonPrimitive(false));
        return StandardResourceSerializers.JSON.serialize(object);
    }
}
