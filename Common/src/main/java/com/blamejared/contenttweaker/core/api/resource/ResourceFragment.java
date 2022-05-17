package com.blamejared.contenttweaker.core.api.resource;

import net.minecraft.server.packs.PackType;

import java.util.Objects;

public interface ResourceFragment {
    record Key(PackType type, String id) {
        public Key {
            Objects.requireNonNull(type);
            Objects.requireNonNull(id);
        }
    }

    <T> void provide(final String path, final T resource, final ResourceSerializer<T> serializer);
    // TODO("Allow access to underlying file system?")
}
