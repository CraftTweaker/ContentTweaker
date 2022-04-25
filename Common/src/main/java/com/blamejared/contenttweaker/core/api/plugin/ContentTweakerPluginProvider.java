package com.blamejared.contenttweaker.core.api.plugin;

public interface ContentTweakerPluginProvider {
    default void registerObjectTypes(final ObjectTypeRegistration registration) {}
    default void registerFactoryMappings(final FactoryMappingRegistration registration) {}
}
