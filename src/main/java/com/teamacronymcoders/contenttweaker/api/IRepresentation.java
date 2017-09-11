package com.teamacronymcoders.contenttweaker.api;

public interface IRepresentation<T> extends ICTObject<T> {
    String getName();

    String getTypeName();

    void register();
}
