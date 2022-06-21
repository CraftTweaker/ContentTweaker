package com.blamejared.contenttweaker.core.api;

import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.object.RegistryResolver;
import com.blamejared.contenttweaker.core.api.registry.GameRegistry;
import com.blamejared.contenttweaker.core.api.registry.RegistryButler;
import com.blamejared.contenttweaker.core.api.resource.ResourceManager;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public interface ApiBridge {
    RegistryButler registryButler();
    ResourceManager resourceManager();

    <T> RegistryResolver<T> findResolver(final ObjectType<T> type);
    <T> GameRegistry<T> findRegistryFromKey(final ObjectType<T> type, final ResourceKey<? extends Registry<T>> key);
}
