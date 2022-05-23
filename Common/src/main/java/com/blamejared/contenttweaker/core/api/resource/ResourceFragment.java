package com.blamejared.contenttweaker.core.api.resource;

import net.minecraft.server.packs.PackType;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public interface ResourceFragment {
    record Key(PackType type, String id) {
        public Key {
            Objects.requireNonNull(type);
            Objects.requireNonNull(id);
        }
    }

    <T> void provideFixed(final String path, final T resource, final ResourceSerializer<T> serializer);
    <T> void provideOrAlter(final String path, final Supplier<T> creator, final Function<T, T> alterationFunction, final ResourceSerializer<T> serializer);
    // TODO("Allow access to underlying file system?")
}
