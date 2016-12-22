package com.teamacronymcoders.tailoredobjects.api.deserializer;

import com.google.gson.JsonObject;

public interface IDeserializer {
    /*
     * @returns serializer's name. Must be unique.
     */
    String getName();

    /*
     * @param jsonObject a json representation of an object that uses this serializer
     *
     * @returns a boolean representing if the object successfully deserialized
     */
    boolean deserializeObject(JsonObject jsonObject);
}
