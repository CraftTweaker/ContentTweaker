package com.blamejared.contenttweaker.core.registry;

public final class MetaRegistry {
    private final ObjectFactoryRegistry factoryMappings;
    private final ObjectTypeRegistry objectTypes;
    private final ReferenceFactoryRegistry referenceFactories;
    private final RegistryResolverRegistry registryResolverRegistry;

    private MetaRegistry() {
        this.factoryMappings = new ObjectFactoryRegistry();
        this.objectTypes = new ObjectTypeRegistry();
        this.referenceFactories = new ReferenceFactoryRegistry();
        this.registryResolverRegistry = new RegistryResolverRegistry();
    }

    public static MetaRegistry of() {
        return new MetaRegistry();
    }

    public ObjectFactoryRegistry factoryMappings() {
        return this.factoryMappings;
    }

    public ObjectTypeRegistry objectTypes() {
        return this.objectTypes;
    }

    public ReferenceFactoryRegistry referenceFactories() {
        return this.referenceFactories;
    }

    public RegistryResolverRegistry registryResolvers() {
        return this.registryResolverRegistry;
    }
}
