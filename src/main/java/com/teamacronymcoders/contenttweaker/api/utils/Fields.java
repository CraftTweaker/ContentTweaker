package com.teamacronymcoders.contenttweaker.api.utils;

public class Fields {
    public static <T> T getValue(T value, T newValue) {
        return newValue != null ? newValue : value;
    }
}
