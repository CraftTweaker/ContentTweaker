package com.blamejared.contenttweaker.core.api;

import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.registry.RegistryButler;
import com.blamejared.contenttweaker.core.api.resource.ResourceManager;
import net.minecraft.resources.ResourceLocation;

public interface ApiBridge {
    RegistryButler registryButler();
    ResourceManager resourceManager();

    <T> T resolve(final ObjectType<T> type, final ResourceLocation id);
}
