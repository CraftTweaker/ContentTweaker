package com.blamejared.contenttweaker.core.api.object;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.Objects;

public interface ObjectType<T> {

    static <T> ObjectType<T> of(final ResourceKey<? extends Registry<T>> key, final Class<T> type) {
        Objects.requireNonNull(key, "key");
        Objects.requireNonNull(type, "type");
        return new ObjectType<>() {
            @Override
            public ResourceKey<? extends Registry<T>> id() {
                return key;
            }

            @Override
            public Class<T> type() {
                return type;
            }

            @Override
            public String toString() {
                return "[%s]{%s@%s}".formatted(this.id(), this.type().getName(), this.id());
            }
        };
    }

    ResourceKey<? extends Registry<T>> id();
    Class<T> type();
}
