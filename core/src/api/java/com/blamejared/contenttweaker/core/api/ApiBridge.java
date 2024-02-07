package com.blamejared.contenttweaker.core.api;

import com.blamejared.contenttweaker.core.api.action.ContentTweakerAction;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.object.RegistryResolver;
import com.blamejared.contenttweaker.core.api.registry.ContentTweakerRegistry;
import com.blamejared.contenttweaker.core.api.registry.GameRegistry;
import com.blamejared.contenttweaker.core.api.registry.RegistryButler;
import com.blamejared.contenttweaker.core.api.resource.ResourceManager;
import com.blamejared.contenttweaker.core.api.util.Handles;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public interface ApiBridge {
    RegistryButler registryButler();
    ResourceManager resourceManager();
    ContentTweakerRegistry registry();
    Handles.Provider handles();

    <T> GameRegistry<T> getOrCreateRegistryFromKey(final ObjectType<T> type, final ResourceKey<? extends Registry<T>> key);

    void apply(final ContentTweakerAction action);
}
