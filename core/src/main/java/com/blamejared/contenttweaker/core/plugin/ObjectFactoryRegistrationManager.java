package com.blamejared.contenttweaker.core.plugin;

import com.blamejared.contenttweaker.core.api.object.ObjectFactory;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.plugin.ObjectFactoryRegistration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

final class ObjectFactoryRegistrationManager implements ObjectFactoryRegistration {
    private final Map<ObjectType<?>, ObjectFactory<?>> factories;

    private ObjectFactoryRegistrationManager() {
        this.factories = new HashMap<>();
    }

    public static Map<ObjectType<?>, ObjectFactory<?>> get(final Consumer<ObjectFactoryRegistration> consumer) {
        final ObjectFactoryRegistrationManager registration = new ObjectFactoryRegistrationManager();
        consumer.accept(registration);
        return Collections.unmodifiableMap(registration.factories);
    }

    @Override
    public <T, U extends ObjectFactory<T>> void registerFactory(final ObjectType<T> type, final U factory) {
        final ObjectFactory<?> previous = this.factories.get(type);
        if (previous != null) {
            throw new IllegalArgumentException("Attempted double factories for type " + type + " through " + previous + " and " + factory);
        }
        this.factories.put(type, factory);
    }
}
