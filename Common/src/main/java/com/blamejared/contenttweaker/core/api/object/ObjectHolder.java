package com.blamejared.contenttweaker.core.api.object;

import net.minecraft.resources.ResourceLocation;

import java.util.Objects;
import java.util.function.Supplier;

public interface ObjectHolder<T> extends Supplier<T> {
    static <T> ObjectHolder<T> of(final ObjectType<T> type, final ResourceLocation id, final Supplier<T> objectCreator) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(id);
        Objects.requireNonNull(objectCreator);
        return new ObjectHolder<>() {
            @Override
            public ObjectType<T> type() {
                return type;
            }

            @Override
            public ResourceLocation id() {
                return id;
            }

            @Override
            public T get() {
                return objectCreator.get();
            }
        };
    }

    ObjectType<T> type();
    ResourceLocation id();
    @Override T get();
}
