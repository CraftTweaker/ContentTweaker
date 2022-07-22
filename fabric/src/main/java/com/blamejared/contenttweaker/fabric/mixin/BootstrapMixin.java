package com.blamejared.contenttweaker.fabric.mixin;

import net.minecraft.server.Bootstrap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = Bootstrap.class, priority = 999)
public abstract class BootstrapMixin {
    @Redirect(
            method = "bootStrap",
            require = 0,
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/core/Registry;freezeBuiltins()V"
            )
    )
    private static void contenttweaker$bootStrap$delayFreezing() {}
}
