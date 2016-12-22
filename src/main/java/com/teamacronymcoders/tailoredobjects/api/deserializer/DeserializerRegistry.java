package com.teamacronymcoders.tailoredobjects.api.deserializer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class DeserializerRegistry {
    private Map<String, IDeserializer> deserializers;

    public DeserializerRegistry() {
        deserializers = new HashMap<>();
    }

    @Nullable
    public IDeserializer getDeserializer(String name) {
        return this.deserializers.get(name);
    }

    public void registerDeserializer(@Nonnull IDeserializer deserializer) {
        this.deserializers.put(deserializer.getName(), deserializer);
    }
}
