package com.blamejared.contenttweaker.core.plugin;

import com.blamejared.contenttweaker.core.api.object.ObjectFactory;
import com.blamejared.contenttweaker.core.api.object.ObjectFactoryMapping;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.plugin.FactoryMappingRegistration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

final class FactoryMappingRegistrationManager implements FactoryMappingRegistration {
    private final Map<ObjectType<?>, ObjectFactoryMapping<?, ?>> mappings;

    private FactoryMappingRegistrationManager() {
        this.mappings = new HashMap<>();
    }

    public static Map<ObjectType<?>, ObjectFactoryMapping<?, ?>> get(final Consumer<FactoryMappingRegistration> consumer) {
        final FactoryMappingRegistrationManager registration = new FactoryMappingRegistrationManager();
        consumer.accept(registration);
        return Collections.unmodifiableMap(registration.mappings);
    }

    @Override
    public <T, U extends ObjectFactory<T>> void registerMapping(final ObjectType<T> type, final ObjectFactoryMapping<T, U> mapping) {
        final ObjectFactoryMapping<?, ?> previous = this.mappings.get(type);
        if (previous != null) {
            throw new IllegalArgumentException("Attempted double mappings for type " + type + " through " + previous + " and " + mapping);
        }
        this.mappings.put(type, mapping);
    }
}
