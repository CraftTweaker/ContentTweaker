package com.teamacronymcoders.tailoredobjects.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.teamacronymcoders.tailoredobjects.api.deserializer.DeserializerRegistry;
import com.teamacronymcoders.tailoredobjects.api.json.ItemStackDeserializer;
import net.minecraft.item.ItemStack;

public class TailoredObjectsAPI {
    private static TailoredObjectsAPI instance;
    private DeserializerRegistry deserializerRegistry;
    private Gson gson;

    private TailoredObjectsAPI() {
        this.deserializerRegistry = new DeserializerRegistry();
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ItemStack.class, new ItemStackDeserializer());
        gson = builder.create();
    }

    public static TailoredObjectsAPI getInstance() {
        if(instance == null) {
            instance = new TailoredObjectsAPI();
        }
        return instance;
    }

    public DeserializerRegistry getDeserializerRegistry() {
        return this.deserializerRegistry;
    }

    public Gson getGson() {
        return this.gson;
    }
}
