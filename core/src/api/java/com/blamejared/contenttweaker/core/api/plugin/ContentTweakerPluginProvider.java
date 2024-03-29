package com.blamejared.contenttweaker.core.api.plugin;

public interface ContentTweakerPluginProvider {
    default void registerObjectTypes(final ObjectTypeRegistration registration) {}
    default void registerFactoryMappings(final FactoryMappingRegistration registration) {}
    default void registerReferenceFactories(final ReferenceFactoryRegistration registration) {}
    default void registerResolvers(final RegistryResolverRegistration registration) {}
    default void registerCustomBrackets(final CustomBracketRegistration registration) {}
}
