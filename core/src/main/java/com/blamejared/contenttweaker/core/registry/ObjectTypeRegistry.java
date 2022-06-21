package com.blamejared.contenttweaker.core.registry;

import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.util.FreezableMap;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public final class ObjectTypeRegistry {
    private final FreezableMap<ResourceLocation, ObjectType<?>> typesById;
    private final FreezableMap<ResourceKey<? extends Registry<?>>, ObjectType<?>> typesByKey;

    ObjectTypeRegistry() {
        this.typesById = FreezableMap.of();
        this.typesByKey = FreezableMap.of();
    }

    public void registerTypes(final Map<ResourceLocation, ObjectType<?>> types) {
        this.typesById.putAll(types);
        this.typesById.values().stream().filter(it -> it.key() != null).forEach(it -> this.typesByKey.put(it.key(), it));

        this.typesById.freeze();
        this.typesByKey.freeze();
    }

    @Nullable
    public <T> ObjectType<T> get(final ResourceLocation id) {
        return GenericUtil.uncheck(this.typesById.get(Objects.requireNonNull(id)));
    }

    @Nullable
    public <T> ObjectType<T> get(final ResourceKey<? extends Registry<T>> key) {
        return GenericUtil.uncheck(this.typesByKey.get(key));
    }

    public Collection<ObjectType<?>> allTypes() {
        return this.typesById.values();
    }
}
