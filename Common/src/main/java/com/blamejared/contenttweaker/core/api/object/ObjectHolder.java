package com.blamejared.contenttweaker.core.api.object;

import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public interface ObjectHolder<T> extends Supplier<T> {
    ObjectType<T> type();
    ResourceLocation id();
    @Override T get();
}
