package com.blamejared.contenttweaker.core;

import com.blamejared.contenttweaker.core.api.ApiBridge;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.object.RegistryResolver;
import com.blamejared.contenttweaker.core.api.registry.ContentTweakerRegistry;
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
    public ContentTweakerRegistry registry() {
        return ContentTweakerCore.core().apiWrapper();
    }

    @Override
    public <T> GameRegistry<T> getOrCreateRegistryFromKey(final ObjectType<T> type, final ResourceKey<? extends Registry<T>> key) {
        return ServiceManager.platform().findRegistryFromKey(type, key);
    }
}
