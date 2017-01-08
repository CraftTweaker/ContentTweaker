package com.teamacronymcoders.contenttweaker.api.json.content;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.teamacronymcoders.contenttweaker.api.utils.ResourceList;

import java.lang.reflect.Type;

public class ResourceDeserializer<T> implements JsonDeserializer<T> {
    private ResourceList<T> resourceList;
    public ResourceDeserializer(ResourceList<T> resourceList) {
        this.resourceList = resourceList;
    }

    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return resourceList.getResource(json.getAsString());
    }
}
