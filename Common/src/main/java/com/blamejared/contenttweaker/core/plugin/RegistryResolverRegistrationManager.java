package com.blamejared.contenttweaker.core.plugin;

import com.blamejared.contenttweaker.core.api.object.RegistryResolver;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.plugin.RegistryResolverRegistration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public final class RegistryResolverRegistrationManager implements RegistryResolverRegistration {
    private final Map<ObjectType<?>, RegistryResolver<?>> resolvers;

    private RegistryResolverRegistrationManager() {
        this.resolvers = new HashMap<>();
    }

    public static Map<ObjectType<?>, RegistryResolver<?>> get(final Consumer<RegistryResolverRegistration> consumer) {
        final RegistryResolverRegistrationManager registration = new RegistryResolverRegistrationManager();
        consumer.accept(registration);
        return Collections.unmodifiableMap(registration.resolvers);
    }

    @Override
    public <T> void register(final ObjectType<T> type, final RegistryResolver<T> resolver) {
        final RegistryResolver<?> previous = this.resolvers.get(type);
        if (previous != null) {
            throw new IllegalArgumentException("Attempted double resolvers for type " + type + " through " + previous + " and " + resolver);
        }
        this.resolvers.put(type, resolver);
    }
}
