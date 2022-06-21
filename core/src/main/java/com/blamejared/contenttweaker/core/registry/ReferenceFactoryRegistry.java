package com.blamejared.contenttweaker.core.registry;

import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.object.ReferenceFactory;
import com.blamejared.contenttweaker.core.api.zen.object.Reference;
import com.blamejared.contenttweaker.core.util.FreezableMap;
import com.blamejared.crafttweaker.api.util.GenericUtil;

import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;

public final class ReferenceFactoryRegistry {
    private final FreezableMap<ObjectType<?>, ReferenceFactory<?, ?>> factories;

    ReferenceFactoryRegistry() {
        this.factories = FreezableMap.of();
    }

    public void registerFactories(final ObjectTypeRegistry objectTypeRegistry, final Map<ObjectType<?>, ReferenceFactory<?, ?>> map) {
        final Collection<ObjectType<?>> objectTypes = objectTypeRegistry.allTypes();
        final Collection<ObjectType<?>> unregisteredTypes = map.keySet()
                .stream()
                .filter(Predicate.not(objectTypes::contains))
                .toList();
        if (!unregisteredTypes.isEmpty()) {
            throw new IllegalStateException("Unknown object types " + unregisteredTypes);
        }
        this.factories.putAll(map);
        this.factories.freeze();
    }

    public <T, U extends Reference<T>> ReferenceFactory<T, U> findFactoryFor(final ObjectType<T> type) {
        return GenericUtil.uncheck(this.factories.get(type));
    }
}
