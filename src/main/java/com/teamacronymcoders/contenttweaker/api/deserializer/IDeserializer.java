package com.teamacronymcoders.contenttweaker.api.deserializer;

import com.google.gson.JsonObject;

import javax.annotation.Nonnull;

public interface IDeserializer {
    /*
     * @returns serializer's name. Must be unique.
     */
    @Nonnull
    String getName();

    /*
     * @param jsonObject a json representation of an object that uses this deserializer
     *
     * @returns a boolean representing if the object successfully deserialized
     */
    boolean deserializeObject(@Nonnull JsonObject jsonObject);
}
