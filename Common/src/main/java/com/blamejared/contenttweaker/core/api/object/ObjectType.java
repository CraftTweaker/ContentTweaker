package com.blamejared.contenttweaker.core.api.object;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public interface ObjectType<T> {
    static <T> ObjectType<T> of(final ResourceKey<? extends Registry<T>> key, final Class<T> type) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(type);
        return of(key.location(), key, type);
    }

    static <T> ObjectType<T> of(final ResourceLocation id, final Class<T> type) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(type);
        return of(id, null, type);
    }

    static <T> ObjectType<T> of(final ResourceLocation id, @Nullable final ResourceKey<? extends Registry<T>> key, final Class<T> type) {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(type, "type");
        final char typeMarker = key == null? 'S' : 'R';
        return new ObjectType<>() {
            @Override
            public ResourceLocation id() {
                return id;
            }

            @Override
            public ResourceKey<? extends Registry<T>> key() {
                return key;
            }

            @Override
            public Class<T> type() {
                return type;
            }

            @Override
            public String toString() {
                return "%s[%s]{%s}".formatted(this.id(), typeMarker, this.type().getName());
            }
        };
    }

    ResourceLocation id();
    @Nullable ResourceKey<? extends Registry<T>> key();
    Class<T> type();
}
