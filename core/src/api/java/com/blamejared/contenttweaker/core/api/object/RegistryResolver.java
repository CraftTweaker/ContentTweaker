package com.blamejared.contenttweaker.core.api.object;

import com.blamejared.contenttweaker.core.api.registry.GameRegistry;
import com.google.common.base.Suppliers;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public interface RegistryResolver<T> extends Function<ResourceLocation, T> {
    static <T> RegistryResolver<T> of(final ObjectType<T> type) {
        final ResourceKey<? extends Registry<T>> key = Objects.requireNonNull(Objects.requireNonNull(type).key());
        return of(type, () -> GameRegistry.findFromKey(type, key));
    }

    static <T> RegistryResolver<T> of(final ObjectType<T> type, final Supplier<? extends GameRegistry<T>> registryResolver) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(registryResolver);
        final Supplier<? extends GameRegistry<T>> resolver = Suppliers.memoize(registryResolver::get);
        return new RegistryResolver<>() {
            @Override
            public ObjectType<T> type() {
                return type;
            }

            @Override
            public GameRegistry<T> registry() {
                return resolver.get();
            }
        };
    }

    ObjectType<T> type();
    GameRegistry<T> registry();

    default T resolve(final ResourceLocation id) {
        return this.registry().get(Objects.requireNonNull(id));
    }

    default ResourceLocation nameOf(final T object) {
        return this.registry().nameOf(Objects.requireNonNull(object));
    }

    default void enqueueRegistration(final ResourceLocation name, final Supplier<T> objectCreator) {
        this.registry().enqueueRegistration(name, objectCreator);
    }

    default void enqueueRegistration(final ObjectHolder<T> holder) {
        this.registry().enqueueRegistration(holder);
    }

    @Override
    default T apply(final ResourceLocation location) {
        return this.resolve(location);
    }
}
