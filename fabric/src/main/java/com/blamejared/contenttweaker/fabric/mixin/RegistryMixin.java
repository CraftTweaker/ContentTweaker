package com.blamejared.contenttweaker.fabric.mixin;

import com.blamejared.contenttweaker.core.ContentTweakerCore;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.fabric.registry.FabricGameRegistry;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import net.minecraft.core.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;

@Mixin(value = Registry.class, priority = Integer.MAX_VALUE)
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
        contenttweaker$freezeBuiltins$registerCotObjects0($$1);
    }

    @Unique
    private static <T> void contenttweaker$freezeBuiltins$registerCotObjects0(final Registry<T> registry) {
        final ObjectType<T> type = ContentTweakerCore.core().metaRegistry().objectTypes().get(registry.key());
        if (type == null) {
            ContentTweakerCore.LOGGER.info("Unknown registry '{}': are you missing object types for it?", registry.key());
            return;
        }
        ContentTweakerCore.LOGGER.info("Registering objects for type '{}'", type);
        try {
            FabricGameRegistry.of(registry, type).doRegistration();
        } catch (final Throwable e) {
            CraftTweakerAPI.LOGGER.error("A critical internal ContentTweaker error occurred", e);
            ContentTweakerCore.LOGGER.error("A critical internal ContentTweaker error occurred", e);
        }
    }
}
