package com.blamejared.contenttweaker.core.api.plugin;

import com.blamejared.contenttweaker.core.api.object.ObjectResolver;
import com.blamejared.contenttweaker.core.api.object.ObjectType;

public interface ResolverRegistration {
    <T> void register(final ObjectType<T> type, final ObjectResolver<T> resolver);
}
