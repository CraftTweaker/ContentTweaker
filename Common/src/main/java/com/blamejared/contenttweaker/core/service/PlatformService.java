package com.blamejared.contenttweaker.core.service;

import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.registry.GameRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.nio.file.Path;

public interface PlatformService {
    Path gameDirectory();
    Path locateResource(final String... components);
    <T> GameRegistry<T> findRegistryFromKey(final ObjectType<T> type, final ResourceKey<? extends Registry<T>> key);
}
