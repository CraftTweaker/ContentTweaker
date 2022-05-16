package com.blamejared.contenttweaker.core.api.resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import java.nio.charset.StandardCharsets;

public final class StandardResourceSerializers {
    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();

    public static final ResourceSerializer<byte[]> BYTE_ARRAY = it -> it;
    public static final ResourceSerializer<String> STRING = it -> it.getBytes(StandardCharsets.UTF_8);
    public static final ResourceSerializer<? extends JsonElement> JSON = it -> STRING.serialize(GSON.toJson(it));

    private StandardResourceSerializers() {}
}
