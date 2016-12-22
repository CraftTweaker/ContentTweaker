package com.teamacronymcoders.tailoredobjects.api.deserializer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.annotation.Nonnull;

public interface IDeserializer {
    /*
     * @returns serializer's name. Must be unique.
     */
    @Nonnull
    String getName();

    /*
     * @param gson Gson object that can be used to deserialize
     * @param jsonObject a json representation of an object that uses this deserializer
     *
     * @returns a boolean representing if the object successfully deserialized
     */
    boolean deserializeObject(@Nonnull Gson gson, @Nonnull JsonObject jsonObject);
}
