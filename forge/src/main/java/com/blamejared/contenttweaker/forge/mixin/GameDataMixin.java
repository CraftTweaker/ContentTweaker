package com.blamejared.contenttweaker.forge.mixin;

import com.blamejared.contenttweaker.core.ContentTweakerCore;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.forge.registry.GameRegistryFactory;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.GameData;
import net.minecraftforge.registries.RegisterEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;
import java.util.Set;

@Mixin(value = GameData.class, remap = false, priority = Integer.MAX_VALUE)
public abstract class GameDataMixin {

    @Inject(
            method = "postRegisterEvents",
            locals = LocalCapture.CAPTURE_FAILHARD,
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraftforge/fml/ModLoader;postEventWithWrapInModOrder(Lnet/minecraftforge/eventbus/api/Event;Ljava/util/function/BiConsumer;Ljava/util/function/BiConsumer;)V",
                    shift = At.Shift.AFTER
            )
    )
    private static void contenttweaker$postRegistryEventDispatch$registerCotObjects(
            CallbackInfo ci,
            Set<ResourceLocation> keySet,
            Set<ResourceLocation> ordered,
            RuntimeException aggregate,
            Iterator<?> var3,
            ResourceLocation rootRegistryName,
            ResourceKey<?> registryKey,
            ForgeRegistry<?> forgeRegistry,
            Registry<?> vanillaRegistry
    ) {
        if (forgeRegistry == null && vanillaRegistry == null) {
            ContentTweakerCore.LOGGER.warn("No registry exists for key '{}': this makes no sense; registration will not be carried out", registryKey);
        } else {
            contenttweaker$postRegistryEventDispatch$registerCotObjects0(GenericUtil.uncheck(registryKey));
        }
    }

    @Unique
    private static <T> void contenttweaker$postRegistryEventDispatch$registerCotObjects0(final ResourceKey<? extends Registry<T>> key) {
        final ObjectType<T> type = ContentTweakerCore.core().metaRegistry().objectTypes().get(key);
        if (type == null) {
            ContentTweakerCore.LOGGER.info("Unknown registry '{}': are you missing an object type for it?", key);
            return;
        }
        ContentTweakerCore.LOGGER.info("Registering objects for type '{}'", type);
        try {
            GameRegistryFactory.findRegistryFromTypeAlone(type).doRegistration();
        } catch (final Throwable e) {
            CraftTweakerAPI.LOGGER.error("A critical internal ContentTweaker error occurred", e);
            ContentTweakerCore.LOGGER.error("A critical internal ContentTweaker error occurred", e);
        }
    }
}
