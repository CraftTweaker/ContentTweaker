package com.blamejared.contenttweaker.fabric.mixin;

import net.minecraft.core.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;

@Mixin(Registry.class)
public abstract class RegistryMixin {

    @Inject(
            method = "freezeBuiltins()V",
            locals = LocalCapture.CAPTURE_FAILHARD,
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/core/Registry;freeze()Lnet/minecraft/core/Registry;"
            )
    )
    private static void contenttweaker$freezeBuiltins$registerCotObjects(
            final CallbackInfo info,
            final Iterator<?> $$0,
            final Registry<?> $$1
    ) {
        //
    }
}
