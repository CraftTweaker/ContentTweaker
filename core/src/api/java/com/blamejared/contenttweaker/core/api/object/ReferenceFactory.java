package com.blamejared.contenttweaker.core.api.object;

import com.blamejared.contenttweaker.core.api.zen.object.Reference;
import com.google.gson.reflect.TypeToken;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface ReferenceFactory<T, U extends Reference<T>> {
    static <T, U extends Reference<T>> ReferenceFactory<T, U> of(final TypeToken<U> type, final Function<ResourceLocation, U> creator) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(creator);
        return of(type, (x, id) -> creator.apply(id));
    }

    static <T, U extends Reference<T>> ReferenceFactory<T, U> of(final TypeToken<U> type, final BiFunction<ObjectType<T>, ResourceLocation, U> creator) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(creator);
        return new ReferenceFactory<>() {
            @Override
            public U of(final ObjectType<T> type, final ResourceLocation id) {
                return creator.apply(type, id);
            }

            @Override
            public TypeToken<U> type() {
                return type;
            }
        };
    }

    U of(final ObjectType<T> type, final ResourceLocation id);
    TypeToken<U> type();
}
