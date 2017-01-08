package com.teamacronymcoders.contenttweaker.representations;

import com.google.gson.JsonObject;

public class ObjectRepresentation {
    private String name;
    private String type;
    private JsonObject object;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public JsonObject getObject() {
        return object;
    }

    public void setObject(JsonObject object) {
        this.object = object;
    }
}
