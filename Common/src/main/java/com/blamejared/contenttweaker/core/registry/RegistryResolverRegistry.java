package com.blamejared.contenttweaker.core.registry;

import com.blamejared.contenttweaker.core.api.object.RegistryResolver;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.util.FreezableMap;
import com.blamejared.crafttweaker.api.util.GenericUtil;

import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;

public final class RegistryResolverRegistry {
    private final FreezableMap<ObjectType<?>, RegistryResolver<?>> resolvers;

    RegistryResolverRegistry() {
        this.resolvers = FreezableMap.of();
    }

    public void registerResolvers(final ObjectTypeRegistry objectTypeRegistry, final Map<ObjectType<?>, RegistryResolver<?>> map) {
        final Collection<ObjectType<?>> objectTypes = objectTypeRegistry.allTypes();
        final Collection<ObjectType<?>> unregisteredTypes = map.keySet()
                .stream()
                .filter(Predicate.not(objectTypes::contains))
                .toList();
        if (!unregisteredTypes.isEmpty()) {
            throw new IllegalStateException("Unknown object types " + unregisteredTypes);
        }
        this.resolvers.putAll(map);
        this.resolvers.freeze();
    }

    public <T> RegistryResolver<T> findResolverFor(final ObjectType<T> type) {
        return GenericUtil.uncheck(this.resolvers.get(type));
    }
}
