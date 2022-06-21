package com.blamejared.contenttweaker.core.api.plugin;

import com.blamejared.contenttweaker.core.api.object.RegistryResolver;
import com.blamejared.contenttweaker.core.api.object.ObjectType;

public interface RegistryResolverRegistration {
    <T> void register(final ObjectType<T> type, final RegistryResolver<T> resolver);
}
