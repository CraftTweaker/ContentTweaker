package com.teamacronymcoders.contenttweaker.modules.json;

import com.google.gson.JsonObject;
import com.teamacronymcoders.contenttweaker.api.json.JsonRequired;

public class ContentHolder {
    @JsonRequired
    private String name;
    @JsonRequired
    private String type;
    @JsonRequired
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
