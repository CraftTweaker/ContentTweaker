package com.blamejared.contenttweaker.fabric.mixin;

import com.blamejared.contenttweaker.fabric.registry.FabricRegistrationManager;
import net.minecraft.core.Registry;
import net.minecraft.server.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Main.class, priority = Integer.MAX_VALUE)
public abstract class MainMixin {
    @Inject(
            method = "main",
            remap = false,
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/Util;startTimerHackThread()V",
                    shift = At.Shift.AFTER
            )
    )
    private static void contenttweaker$main$freezeRegistries(final String[] args, final CallbackInfo info) {
        FabricRegistrationManager.registerAll(Registry.REGISTRY);
    }
}
