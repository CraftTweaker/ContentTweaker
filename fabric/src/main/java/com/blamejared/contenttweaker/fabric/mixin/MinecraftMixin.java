package com.blamejared.contenttweaker.fabric.mixin;

import com.blamejared.contenttweaker.fabric.registry.FabricRegistrationManager;
import com.blamejared.contenttweaker.fabric.resource.FabricResourceManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfig;
import net.minecraft.core.Registry;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.PackRepository;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Shadow
    public abstract PackRepository getResourcePackRepository();

    @Inject(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/packs/repository/PackRepository;reload()V"
            )
    )
    private void contenttweaker$init$injectCustomRepositorySources(final GameConfig config, final CallbackInfo info) {
        FabricResourceManager.inject(PackType.CLIENT_RESOURCES, ((PackRepositoryAccessor) this.getResourcePackRepository()).contenttweaker$sources()::add);
    }

    @Inject(
            method = "<init>",
            at = @At(
                    value = "INVOKE_ASSIGN",
                    target = "Ljava/lang/Thread;currentThread()Ljava/lang/Thread;",
                    shift = At.Shift.AFTER
            )
    )
    private void contenttweaker$init$freezeRegistries(final GameConfig config, final CallbackInfo info) {
        FabricRegistrationManager.registerAll(Registry.REGISTRY);
    }
}
