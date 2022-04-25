package com.blamejared.contenttweaker.core.registry;

import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.util.FreezableMap;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Map;

public final class ObjectTypeRegistry {
    private final FreezableMap<ResourceKey<? extends Registry<?>>, ObjectType<?>> types;

    ObjectTypeRegistry() {
        this.types = FreezableMap.of();
    }

    public void registerTypes(final Map<ResourceKey<? extends Registry<?>>, ObjectType<?>> types) {
        this.types.putAll(types);
        this.types.freeze();
    }

    @Nullable
    public <T> ObjectType<T> get(final ResourceKey<? extends Registry<T>> key) {
        return GenericUtil.uncheck(this.types.get(key));
    }

    public Collection<ObjectType<?>> allTypes() {
        return this.types.values();
    }
}
