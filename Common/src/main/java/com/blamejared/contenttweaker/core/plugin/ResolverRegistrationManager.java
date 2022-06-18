package com.blamejared.contenttweaker.core.plugin;

import com.blamejared.contenttweaker.core.api.object.ObjectResolver;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.object.ReferenceFactory;
import com.blamejared.contenttweaker.core.api.plugin.ReferenceFactoryRegistration;
import com.blamejared.contenttweaker.core.api.plugin.ResolverRegistration;
import com.blamejared.contenttweaker.core.api.zen.object.Reference;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public final class ResolverRegistrationManager implements ResolverRegistration {
    private final Map<ObjectType<?>, ObjectResolver<?>> resolvers;

    private ResolverRegistrationManager() {
        this.resolvers = new HashMap<>();
    }

    public static Map<ObjectType<?>, ObjectResolver<?>> get(final Consumer<ResolverRegistration> consumer) {
        final ResolverRegistrationManager registration = new ResolverRegistrationManager();
        consumer.accept(registration);
        return Collections.unmodifiableMap(registration.resolvers);
    }

    @Override
    public <T> void register(final ObjectType<T> type, final ObjectResolver<T> resolver) {
        final ObjectResolver<?> previous = this.resolvers.get(type);
        if (previous != null) {
            throw new IllegalArgumentException("Attempted double resolvers for type " + type + " through " + previous + " and " + resolver);
        }
        this.resolvers.put(type, resolver);
    }
}
