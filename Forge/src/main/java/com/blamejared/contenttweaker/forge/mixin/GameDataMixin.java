package com.blamejared.contenttweaker.forge.mixin;

import com.blamejared.contenttweaker.core.ContentTweakerCore;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.registry.GameRegistry;
import com.blamejared.contenttweaker.forge.registry.ForgeGameRegistry;
import com.blamejared.contenttweaker.forge.registry.VanillaGameRegistry;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.IModStateTransition;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.GameData;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.function.BiFunction;

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
        contenttweaker$postRegistryEventDispatch$registerCotObjects0($$3);
    }

    @Inject(
            method = "postVanillaRegisterEvent(Lnet/minecraft/core/Registry;)V",
            locals = LocalCapture.NO_CAPTURE,
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraftforge/registries/GameData;applyHolderLookups(Lnet/minecraft/resources/ResourceLocation;)V"
            )
    )
    private static void contenttweaker$postVanillaRegisterEvent$registerCotObjects(
            final Registry<?> $$0,
            final CallbackInfo info
    ) {
        contenttweaker$postVanillaRegisterEvent$registerCotObjects0($$0);
    }

    @Unique
    private static <T extends IForgeRegistryEntry<T>> void contenttweaker$postRegistryEventDispatch$registerCotObjects0(final ForgeRegistry<T> registry) {
        contenttweaker$registerCotObjects0(registry, registry.getRegistryKey(), ForgeGameRegistry::of);
    }

    @Unique
    private static <T> void contenttweaker$postVanillaRegisterEvent$registerCotObjects0(final Registry<T> registry) {
        contenttweaker$registerCotObjects0(registry, registry.key(), VanillaGameRegistry::of);
    }

    @Unique
    private static <T, U> void contenttweaker$registerCotObjects0(
            final U registry,
            final ResourceKey<? extends Registry<T>> key,
            final BiFunction<ObjectType<T>, U, GameRegistry<T>> creator
    ) {
        final ObjectType<T> type = ContentTweakerCore.core().metaRegistry().objectTypes().get(key);
        if (type == null) {
            ContentTweakerCore.LOGGER.info("Unknown registry '{}': are you missing an object type for it?", key);
            return;
        }
        ContentTweakerCore.LOGGER.info("Registering objects for type '{}'", type);
        try {
            ContentTweakerCore.core().registryButler().executeForRegistry(creator.apply(type, registry));
        } catch (final Throwable e) {
            CraftTweakerAPI.LOGGER.error("A critical internal ContentTweaker error occurred", e);
            ContentTweakerCore.LOGGER.error("A critical internal ContentTweaker error occurred", e);
        }
    }
}
