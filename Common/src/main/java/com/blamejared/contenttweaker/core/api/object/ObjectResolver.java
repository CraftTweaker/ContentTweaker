package com.blamejared.contenttweaker.core.api.object;

import com.blamejared.crafttweaker.api.util.GenericUtil;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;
import java.util.function.Function;

public interface ObjectResolver<T> extends Function<ResourceLocation, T> {
    static <T> ObjectResolver<T> of(final ObjectType<T> type) {
        final ResourceKey<? extends Registry<T>> key = Objects.requireNonNull(Objects.requireNonNull(type).key());
        return of(type, it -> GenericUtil.uncheck(Objects.requireNonNull(Registry.REGISTRY.get(key.location())).get(it)));
    }

    static <T> ObjectResolver<T> of(final ObjectType<T> type, final Function<ResourceLocation, T> resolver) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(resolver);
        final Function<ResourceLocation, T> decoratedResolver = resolver.compose(Objects::requireNonNull);
        return new ObjectResolver<>() {
            @Override
            public ObjectType<T> type() {
                return type;
            }

            @Override
            public T resolve(final ResourceLocation id) {
                return decoratedResolver.apply(id);
            }
        };
    }

    ObjectType<T> type();
    T resolve(final ResourceLocation id);

    @Override
    default T apply(final ResourceLocation location) {
        return this.resolve(location);
    }
}
