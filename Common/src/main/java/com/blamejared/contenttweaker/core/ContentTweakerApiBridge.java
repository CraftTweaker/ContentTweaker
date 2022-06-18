package com.blamejared.contenttweaker.core;

import com.blamejared.contenttweaker.core.api.ApiBridge;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.registry.RegistryButler;
import com.blamejared.contenttweaker.core.api.resource.ResourceManager;
import net.minecraft.resources.ResourceLocation;

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
    public <T> T resolve(final ObjectType<T> type, final ResourceLocation id) {
        return ContentTweakerCore.core().metaRegistry().resolvers().findResolverFor(type).resolve(id);
    }
}
