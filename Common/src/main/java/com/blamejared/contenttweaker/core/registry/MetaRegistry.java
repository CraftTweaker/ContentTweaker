package com.blamejared.contenttweaker.core.registry;

public final class MetaRegistry {
    private final FactoryMappingsRegistry factoryMappings;
    private final ObjectTypeRegistry objectTypes;

    private MetaRegistry() {
        this.factoryMappings = new FactoryMappingsRegistry();
        this.objectTypes = new ObjectTypeRegistry();
    }

    public static MetaRegistry of() {
        return new MetaRegistry();
    }

    public FactoryMappingsRegistry factoryMappings() {
        return this.factoryMappings;
    }

    public ObjectTypeRegistry objectTypes() {
        return this.objectTypes;
    }
}
