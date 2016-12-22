package com.teamacronymcoders.tailoredobjects.api;

import com.teamacronymcoders.tailoredobjects.api.deserializer.DeserializerRegistry;

public class TailoredObjectsAPI {
    private static TailoredObjectsAPI instance;
    private DeserializerRegistry deserializerRegistry;

    private TailoredObjectsAPI() {
        this.deserializerRegistry = new DeserializerRegistry();
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
}
