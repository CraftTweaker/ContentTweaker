package com.blamejared.contenttweaker.fabric.mixin;

import com.blamejared.contenttweaker.fabric.resource.FabricResourceManager;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.RepositorySource;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.LinkedHashSet;
import java.util.Set;

@Mixin(value = PackRepository.class, priority = 1001)
public abstract class PackRepositoryMixin {
    @Unique
    private static final Set<String> CONTENTTWEAKER$CLIENT_SOURCE_NAMES = Set.of("ClientPackSource", "class_1065");

    @Unique
    private static final Set<String> CONTENTTWEAKER$SERVER_SOURCE_NAMES = Set.of("ServerPacksSource", "class_3286");

    @Final
    @Mutable
    @Shadow
    private Set<RepositorySource> sources;

    @Inject(
            method = "<init>([Lnet/minecraft/server/packs/repository/RepositorySource;)V",
            at = @At(value = "RETURN")
    )
    private void contenttweaker$init$allowSourcesMutation(final RepositorySource[] sources, final CallbackInfo info) {
        final Set<RepositorySource> previous = Set.copyOf(this.sources);
        this.sources = new LinkedHashSet<>(Set.of(sources));
        this.sources.addAll(previous);
    }

    @Inject(
            method = "<init>([Lnet/minecraft/server/packs/repository/RepositorySource;)V",
            at = @At(value = "RETURN")
    )
    private void contenttweaker$init$injectCustomRepositorySources(final RepositorySource[] sources, final CallbackInfo info) {
        final PackType type = this.contenttweaker$determineTypeBasedOnSources(this.sources);
        FabricResourceManager.inject(type, this.sources::add);
    }

    @Unique
    private PackType contenttweaker$determineTypeBasedOnSources(final Set<RepositorySource> sources) {
        for (final RepositorySource source : sources) {
            final Class<?> c = source.getClass();
            final String simpleName = c.getSimpleName();

            if (CONTENTTWEAKER$CLIENT_SOURCE_NAMES.contains(simpleName)) {
                return PackType.CLIENT_RESOURCES;
            }

            if (CONTENTTWEAKER$SERVER_SOURCE_NAMES.contains(simpleName)) {
                return PackType.SERVER_DATA;
            }
        }
        throw new IllegalStateException("No sources: this is impossible -- report to ContentTweaker");
    }
}
