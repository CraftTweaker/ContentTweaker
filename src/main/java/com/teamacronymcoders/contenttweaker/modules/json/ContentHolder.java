package com.teamacronymcoders.contenttweaker.modules.json;

import com.google.gson.JsonObject;

public class ContentHolder {
    private String name;
    private String type;
    private JsonObject content;

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

    public JsonObject getContent() {
        return content;
    }

    public void setContent(JsonObject content) {
        this.content = content;
    }
}
