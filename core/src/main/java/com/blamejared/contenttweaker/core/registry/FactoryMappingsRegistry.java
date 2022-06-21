package com.blamejared.contenttweaker.core.registry;

import com.blamejared.contenttweaker.core.api.object.ObjectFactory;
import com.blamejared.contenttweaker.core.api.object.ObjectFactoryMapping;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.util.FreezableMap;
import com.blamejared.crafttweaker.api.util.GenericUtil;

import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;

public final class FactoryMappingsRegistry {
    private final FreezableMap<ObjectType<?>, ObjectFactoryMapping<?, ?>> factories;

    FactoryMappingsRegistry() {
        this.factories = FreezableMap.of();
    }

    public void registerMappings(final ObjectTypeRegistry objectTypeRegistry, final Map<ObjectType<?>, ObjectFactoryMapping<?, ?>> map) {
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

    public <T, U extends ObjectFactory<T>> ObjectFactoryMapping<T, U> findMappingFor(final ObjectType<T> type) {
        return GenericUtil.uncheck(this.factories.get(type));
    }
}
