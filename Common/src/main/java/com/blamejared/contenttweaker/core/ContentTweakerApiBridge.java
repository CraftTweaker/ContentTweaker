package com.blamejared.contenttweaker.core;

import com.blamejared.contenttweaker.core.api.ApiBridge;
import com.blamejared.contenttweaker.core.api.registry.RegistryButler;
import com.blamejared.contenttweaker.core.api.resource.ResourceManager;

public final class ContentTweakerApiBridge implements ApiBridge {
    @Override
    public RegistryButler registryButler() {
        return ContentTweakerCore.core().registryButler();
    }

    @Override
    public ResourceManager resourceManager() {
        return ContentTweakerCore.core().resourceManager();
    }
}
