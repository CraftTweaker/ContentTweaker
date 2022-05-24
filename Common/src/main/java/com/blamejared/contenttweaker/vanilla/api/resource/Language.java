package com.blamejared.contenttweaker.vanilla.api.resource;

import com.blamejared.contenttweaker.core.api.resource.ResourceSerializer;
import com.blamejared.contenttweaker.core.api.resource.StandardResourceSerializers;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class Language {
    public static final ResourceSerializer<Language> SERIALIZER = Language::serialize;

    private final Map<String, String> keys;

    private Language() {
        this.keys = new HashMap<>();
    }

    public static Language of() {
        return new Language();
    }

    public Language add(final String key, final String text) {
        Objects.requireNonNull(text);
        if (this.keys.containsKey(Objects.requireNonNull(key))) {
            throw new IllegalArgumentException("Key " + key + " already exists");
        }
        this.keys.put(key, text);
        return this;
    }

    public Language item(final ResourceLocation name, final String text) {
        return this.add("item.%s.%s".formatted(Objects.requireNonNull(name).getNamespace(), name.getPath()), Objects.requireNonNull(text));
    }

    public Language tab(final String name, final String text) {
        return this.add("itemGroup.%s".formatted(Objects.requireNonNull(name)), Objects.requireNonNull(text));
    }

    private byte[] serialize() {
        final JsonObject object = new JsonObject();
        this.keys.forEach(object::addProperty);
        return StandardResourceSerializers.JSON.serialize(object);
    }
}
