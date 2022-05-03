package com.blamejared.contenttweaker.core.registry;

import com.blamejared.contenttweaker.core.api.object.ObjectHolder;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import net.minecraft.resources.ResourceLocation;

public interface GameRegistry<T> {
    ObjectType<T> type();
    T get(final ResourceLocation name);
    void register(final ResourceLocation name, final T object);

    default void register(final ObjectHolder<T> holder) {
        this.register(holder.id(), holder.get());
    }
}
