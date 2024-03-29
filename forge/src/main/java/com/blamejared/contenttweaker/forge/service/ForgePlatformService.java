package com.blamejared.contenttweaker.forge.service;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.registry.GameRegistry;
import com.blamejared.contenttweaker.core.service.PlatformService;
import com.blamejared.contenttweaker.forge.registry.GameRegistryFactory;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;
import java.util.Objects;

public final class ForgePlatformService implements PlatformService {
    @Override
    public Path gameDirectory() {
        return FMLPaths.GAMEDIR.get();
    }

    @Override
    public Path locateResource(final String... components) {
        Objects.requireNonNull(components);
        if (components.length < 1) {
            throw new IllegalArgumentException("At least one component required");
        }
        return ModList.get()
                .getModFileById(ContentTweakerConstants.MOD_ID)
                .getFile()
                .findResource(components);
    }

    @Override
    public <T> GameRegistry<T> findRegistryFromKey(final ObjectType<T> type, final ResourceKey<? extends Registry<T>> key) {
        return GameRegistryFactory.findRegistryFromKey(type, key);
    }
}
