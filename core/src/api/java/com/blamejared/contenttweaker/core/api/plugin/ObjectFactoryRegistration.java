package com.blamejared.contenttweaker.core.api.plugin;

import com.blamejared.contenttweaker.core.api.object.ObjectFactory;
import com.blamejared.contenttweaker.core.api.object.ObjectType;

public interface ObjectFactoryRegistration {
    <T, U extends ObjectFactory<T>> void registerFactory(final ObjectType<T> type, final U factory);
}
