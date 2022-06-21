package com.blamejared.contenttweaker.core.plugin;

import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.object.ReferenceFactory;
import com.blamejared.contenttweaker.core.api.plugin.ReferenceFactoryRegistration;
import com.blamejared.contenttweaker.core.api.zen.object.Reference;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public final class ReferenceFactoryRegistrationManager implements ReferenceFactoryRegistration {
    private final Map<ObjectType<?>, ReferenceFactory<?, ?>> factories;

    private ReferenceFactoryRegistrationManager() {
        this.factories = new HashMap<>();
    }

    public static Map<ObjectType<?>, ReferenceFactory<?, ?>> get(final Consumer<ReferenceFactoryRegistration> consumer) {
        final ReferenceFactoryRegistrationManager registration = new ReferenceFactoryRegistrationManager();
        consumer.accept(registration);
        return Collections.unmodifiableMap(registration.factories);
    }

    @Override
    public <T, U extends Reference<T>> void register(final ObjectType<T> type, final ReferenceFactory<T, U> factory) {
        final ReferenceFactory<?, ?> previous = this.factories.get(type);
        if (previous != null) {
            throw new IllegalArgumentException("Attempted double factories for type " + type + " through " + previous + " and " + factory);
        }
        this.factories.put(type, factory);
    }
}
