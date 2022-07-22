package com.blamejared.contenttweaker.forge.mixin;

import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.RepositorySource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

@Mixin(PackRepository.class)
public abstract class PackRepositoryMixin {
    @Final
    @Mutable
    @Shadow
    private Set<RepositorySource> sources;

    @Inject(
            method = "<init>(Lnet/minecraft/server/packs/repository/Pack$PackConstructor;[Lnet/minecraft/server/packs/repository/RepositorySource;)V",
            at = @At(value = "RETURN")
    )
    private void contenttweaker$init$keepAdditionOrdering(final Pack.PackConstructor packConstructor, final RepositorySource[] sources, final CallbackInfo info) {
        final Set<RepositorySource> previous = new LinkedHashSet<>(this.sources);
        this.sources = new LinkedHashSet<>(Arrays.asList(sources));
        this.sources.addAll(previous);
    }
}
