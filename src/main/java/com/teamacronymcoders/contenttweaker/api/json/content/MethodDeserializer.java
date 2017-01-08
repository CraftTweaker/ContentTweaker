package com.teamacronymcoders.contenttweaker.api.json.content;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.function.Function;

public class MethodDeserializer<T> implements JsonDeserializer<T> {
    private Function<JsonElement, T> createMethod;
    public MethodDeserializer(Function<JsonElement, T> createMethod) {
        this.createMethod = createMethod;
    }

    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return createMethod.apply(json);
    }
}
