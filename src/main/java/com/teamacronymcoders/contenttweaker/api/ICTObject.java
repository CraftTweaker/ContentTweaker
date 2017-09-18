package com.teamacronymcoders.contenttweaker.api;

public interface ICTObject<T> {
    T getInternal();

    default String getString() {
        return this.getInternal().toString();
    }
}
