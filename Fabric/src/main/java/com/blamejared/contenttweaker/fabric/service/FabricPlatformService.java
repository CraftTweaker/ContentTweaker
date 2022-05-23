package com.blamejared.contenttweaker.fabric.service;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.service.PlatformService;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public final class FabricPlatformService implements PlatformService {
    @Override
    public Path gameDirectory() {
        return FabricLoader.getInstance().getGameDir();
    }

    @Override
    public Path locateResource(final String... components) {
        Objects.requireNonNull(components);
        if (components.length < 1) {
            throw new IllegalArgumentException("At least one component required");
        }
        final List<Path> possiblePaths = FabricLoader.getInstance()
                .getModContainer(ContentTweakerConstants.MOD_ID)
                .orElseThrow(IllegalStateException::new)
                .getRootPaths()
                .stream()
                .map(it -> it.resolve(String.join("/", components)))
                .toList();
        return possiblePaths.size() == 1? possiblePaths.get(0) : possiblePaths.stream()
                .filter(Files::exists)
                .findFirst()
                .orElse(possiblePaths.get(0)); // Guaranteed at least one possible root path
    }
}
