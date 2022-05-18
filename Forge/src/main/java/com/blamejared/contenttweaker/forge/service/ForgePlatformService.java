package com.blamejared.contenttweaker.forge.service;

import com.blamejared.contenttweaker.core.service.PlatformService;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public final class ForgePlatformService implements PlatformService {
    @Override
    public Path gameDirectory() {
        return FMLPaths.GAMEDIR.get();
    }
}
