package com.blamejared.contenttweaker.core.registry;

import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.util.FreezableMap;
import com.blamejared.contenttweaker.core.api.zen.rt.Unknown;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class ObjectTypeRegistry {
    private static final class UnknownType<T> implements ObjectType<T> {
        private final ResourceKey<? extends Registry<T>> key;

        private UnknownType(final ResourceKey<? extends Registry<T>> key) {
            this.key = Objects.requireNonNull(key);
        }

        @Override
        public ResourceKey<? extends Registry<T>> id() {
            return this.key;
        }

        @Override
        public Class<?> type() {
            return Unknown.class;
        }

        @Override
        public String toString() {
            return "[%s]{?}".formatted(this.id().location());
        }
    }

    private final FreezableMap<ResourceKey<? extends Registry<?>>, ObjectType<?>> types;
    private final Map<ResourceKey<? extends Registry<?>>, ObjectType<?>> unknownTypes;

    ObjectTypeRegistry() {
        this.types = FreezableMap.of();
        this.unknownTypes = new HashMap<>();
    }

    public void registerTypes(final Map<ResourceKey<? extends Registry<?>>, ObjectType<?>> types) {
        this.types.putAll(types);
        this.types.freeze();
    }

    @Nullable
    public <T> ObjectType<T> get(final ResourceKey<? extends Registry<T>> key) {
        return GenericUtil.uncheck(this.types.get(key));
    }

    public <T> ObjectType<T> getOrUnknown(final ResourceKey<? extends Registry<T>> key) {
        return Optional.ofNullable(this.get(key))
                .orElseGet(() -> GenericUtil.uncheck(this.unknownTypes.computeIfAbsent(key, it -> new UnknownType<>(key))));
    }

    public Collection<ObjectType<?>> allTypes() {
        return this.types.values();
    }
}
