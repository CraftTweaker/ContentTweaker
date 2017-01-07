package com.teamacronymcoders.tailoredobjects.api.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.teamacronymcoders.tailoredobjects.api.TailoredObjectsAPI;
import net.minecraft.block.material.Material;

import java.lang.reflect.Type;

public class MaterialDeserializer implements JsonDeserializer<Material> {
    @Override
    public Material deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return TailoredObjectsAPI.getInstance().getBlockMaterials().getResource(json.getAsString());
    }
}
