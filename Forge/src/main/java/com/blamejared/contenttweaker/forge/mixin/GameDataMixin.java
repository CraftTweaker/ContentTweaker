package com.blamejared.contenttweaker.forge.mixin;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.IModStateTransition;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.GameData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(GameData.class)
public abstract class GameDataMixin {

    @Inject(
            method = "lambda$postRegistryEventDispatch$19(Lnet/minecraftforge/fml/IModStateTransition$EventGenerator;)V",
            locals = LocalCapture.CAPTURE_FAILHARD,
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraftforge/registries/ForgeRegistry;freeze()V"
            )
    )
    private static void contenttweaker$postRegistryEventDispatch$registerCotObjects(
            final IModStateTransition.EventGenerator<?> $$0,
            final CallbackInfo info,
            final RegistryEvent.Register<?> $$1,
            final ResourceLocation $$2,
            final ForgeRegistry<?> $$3
    ) {
        //
    }
}
