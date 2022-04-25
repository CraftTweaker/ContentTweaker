package com.blamejared.contenttweaker.core.plugin;

import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.plugin.ObjectTypeRegistration;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

final class ObjectTypeRegistrationManager implements ObjectTypeRegistration {
    private final Map<ResourceKey<? extends Registry<?>>, ObjectType<?>> types;

    private ObjectTypeRegistrationManager() {
        this.types = new HashMap<>();
    }

    public static Map<ResourceKey<? extends Registry<?>>, ObjectType<?>> gather(final Consumer<ObjectTypeRegistration> consumer) {
        final ObjectTypeRegistrationManager registration = new ObjectTypeRegistrationManager();
        consumer.accept(registration);
        return Collections.unmodifiableMap(registration.types);
    }

    @Override
    public <T> void registerType(final ObjectType<T> type) {
        final ResourceKey<? extends Registry<?>> id = type.id();
        final ObjectType<?> previous = this.types.get(id);
        if (previous != null) {
            throw new IllegalArgumentException("Attempted double registration for type " + id.location() + "; previous: " + previous + ", current: " + type);
        }
        this.types.put(type.id(), type);
    }

}
