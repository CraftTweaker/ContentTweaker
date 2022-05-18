package com.blamejared.contenttweaker.fabric.service;

import com.blamejared.contenttweaker.core.service.PlatformService;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public final class FabricPlatformService implements PlatformService {
    @Override
    public Path gameDirectory() {
        return FabricLoader.getInstance().getGameDir();
    }
}
