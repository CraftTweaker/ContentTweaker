package com.blamejared.contenttweaker.core.api.registry;

import com.blamejared.contenttweaker.core.api.ContentTweakerApi;
import com.blamejared.contenttweaker.core.api.object.ObjectHolder;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface GameRegistry<T> {
    static <T> GameRegistry<T> findFromKey(final ObjectType<T> type, final ResourceKey<? extends Registry<T>> key) {
        return ContentTweakerApi.get().getOrCreateRegistryFromKey(type, key);
    }

    ObjectType<T> type();
    T get(final ResourceLocation name);
    ResourceLocation nameOf(final T object);
    Collection<T> all();
    void enqueueRegistration(final ResourceLocation name, final Supplier<T> objectCreator);

    default void enqueueRegistration(final ObjectHolder<T> holder) {
        this.enqueueRegistration(holder.id(), holder);
    }

    default Stream<T> stream() {
        return this.all().stream();
    }
}
