package com.blamejared.contenttweaker.forge.mixin;

import com.blamejared.contenttweaker.core.ContentTweakerCore;
import com.blamejared.contenttweaker.core.api.ContentTweakerLoggers;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.forge.registry.GameRegistryFactory;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.ModLoader;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.GameData;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegisterEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;
import java.util.Set;

@Mixin(value = GameData.class, remap = false, priority = Integer.MAX_VALUE)
public abstract class GameDataMixin {

    // This is not a good Mixin, but Forge 1.20.1 doesn't have MixinExtras, so we'll make do with what we have
    // It's not like anyone else is doing this anyway
    @Redirect(
            method = "postRegisterEvents",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraftforge/fml/ModLoader;postEventWrapContainerInModOrder(Lnet/minecraftforge/eventbus/api/Event;)V"
            )
    )
    private static void contenttweaker$postRegistryEventDispatch$registerCotObjects(final ModLoader modLoader, final Event event) {
        final RegisterEvent realEvent = GenericUtil.uncheck(event);
        modLoader.postEventWrapContainerInModOrder(realEvent);

        final IForgeRegistry<?> forgeRegistry = realEvent.getForgeRegistry();
        final Registry<?> vanillaRegistry = realEvent.getVanillaRegistry();
        final ResourceKey<?> registryKey = realEvent.getRegistryKey();

        if (forgeRegistry == null && vanillaRegistry == null) {
            ContentTweakerLoggers.core().warn("No registry exists for key '{}': this makes no sense, registration will not be carried out", registryKey);
            return;
        }

        contenttweaker$postRegistryEventDispatch$registerCotObjects0(GenericUtil.uncheck(registryKey));
    }

    @Unique
    private static <T> void contenttweaker$postRegistryEventDispatch$registerCotObjects0(final ResourceKey<? extends Registry<T>> key) {
        final ObjectType<T> type = ContentTweakerCore.core().metaRegistry().objectTypes().get(key);
        if (type == null) {
            ContentTweakerLoggers.core().info("Unknown registry '{}': are you missing an object type for it?", key);
            return;
        }
        ContentTweakerLoggers.core().info("Registering objects for type '{}'", type);
        try {
            GameRegistryFactory.findRegistryFromTypeAlone(type).doRegistration();
        } catch (final Throwable e) {
            ContentTweakerLoggers.core().error("A critical internal ContentTweaker error occurred", e);
        }
    }
}
