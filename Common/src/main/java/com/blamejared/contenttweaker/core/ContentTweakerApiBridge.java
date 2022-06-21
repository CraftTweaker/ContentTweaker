package com.blamejared.contenttweaker.core;

import com.blamejared.contenttweaker.core.api.ApiBridge;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.object.RegistryResolver;
import com.blamejared.contenttweaker.core.api.registry.GameRegistry;
import com.blamejared.contenttweaker.core.api.registry.RegistryButler;
import com.blamejared.contenttweaker.core.api.resource.ResourceManager;
import com.blamejared.contenttweaker.core.service.ServiceManager;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public final class ContentTweakerApiBridge implements ApiBridge {
    @Override
    public RegistryButler registryButler() {
        return ContentTweakerCore.core().registryButler();
    }

    @Override
    public ResourceManager resourceManager() {
        return ContentTweakerCore.core().resourceManager();
    }

    @Override
    public <T> RegistryResolver<T> findResolver(final ObjectType<T> type) {
        return ContentTweakerCore.core().metaRegistry().registryResolvers().findResolverFor(type);
    }
}
