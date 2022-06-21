package com.blamejared.contenttweaker.core.api.plugin;

import com.blamejared.contenttweaker.core.api.object.ObjectFactory;
import com.blamejared.contenttweaker.core.api.object.ObjectFactoryMapping;
import com.blamejared.contenttweaker.core.api.object.ObjectType;

public interface FactoryMappingRegistration {
    <T, U extends ObjectFactory<T>> void registerMapping(final ObjectType<T> type, final ObjectFactoryMapping<T, U> mapping);

    default <T, U extends ObjectFactory<T>> void registerMapping(final ObjectType<T> type, final Class<U> factoryClass) {
        this.registerMapping(type, () -> factoryClass);
    }
}
