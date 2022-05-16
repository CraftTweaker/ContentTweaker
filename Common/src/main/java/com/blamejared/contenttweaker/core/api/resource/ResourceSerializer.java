package com.blamejared.contenttweaker.core.api.resource;

@FunctionalInterface
public interface ResourceSerializer<T> {
    byte[] serialize(final T resource);
}
