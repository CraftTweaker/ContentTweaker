package com.blamejared.contenttweaker.core.api;

import com.blamejared.contenttweaker.core.api.registry.RegistryButler;
import com.blamejared.contenttweaker.core.api.resource.ResourceManager;

public interface ApiBridge {
    RegistryButler registryButler();
    ResourceManager resourceManager();
}
