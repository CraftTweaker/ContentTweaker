package com.blamejared.contenttweaker.fabric.resource;

import com.blamejared.contenttweaker.core.resource.RuntimeRepositorySource;
import com.blamejared.contenttweaker.core.resource.UserRepositorySource;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.RepositorySource;

import java.util.function.Consumer;
import java.util.stream.Stream;

public final class FabricResourceManager {
    private FabricResourceManager() {}

    public static void inject(final PackType type, final Consumer<RepositorySource> sourceConsumer) {
        Stream.of(RuntimeRepositorySource.of(type), UserRepositorySource.of(type)).forEach(sourceConsumer);
    }
}
