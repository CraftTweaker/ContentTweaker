package com.teamacronymcoders.tailoredobjects.api;

import com.teamacronymcoders.tailoredobjects.api.deserializer.DeserializerRegistry;

public class TailoredObjectsAPI {
    private static TailoredObjectsAPI instance;
    private DeserializerRegistry deserializers;

    private TailoredObjectsAPI() {
        this.deserializers = new DeserializerRegistry();
    }

    private static TailoredObjectsAPI getInstance() {
        if(instance == null) {
            instance = new TailoredObjectsAPI();
        }
        return instance;
    }
}
