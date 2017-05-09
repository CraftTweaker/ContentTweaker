package com.teamacronymcoders.contenttweaker.api;

public interface IRepresentation {
    String getName();

    String getTypeName();

    void register();

    Object getInternal();
}
