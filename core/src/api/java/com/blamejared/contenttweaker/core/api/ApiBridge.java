package com.blamejared.contenttweaker.core.api;

import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.object.RegistryResolver;
import com.blamejared.contenttweaker.core.api.registry.ContentTweakerRegistry;
import com.blamejared.contenttweaker.core.api.registry.GameRegistry;
import com.blamejared.contenttweaker.core.api.registry.RegistryButler;
import com.blamejared.contenttweaker.core.api.resource.ResourceManager;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public interface ApiBridge {
    RegistryButler registryButler();
    ResourceManager resourceManager();
    ContentTweakerRegistry registry();

    <T> GameRegistry<T> getOrCreateRegistryFromKey(final ObjectType<T> type, final ResourceKey<? extends Registry<T>> key);
}
