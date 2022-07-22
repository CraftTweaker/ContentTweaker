package com.blamejared.contenttweaker.fabric.registry;

import com.blamejared.contenttweaker.core.ContentTweakerCore;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import net.minecraft.core.Registry;

public final class FabricRegistrationManager {
    private FabricRegistrationManager() {}

    public static void registerAll(final Registry<? extends Registry<?>> registry) {
        registry.iterator().forEachRemaining(FabricRegistrationManager::registerAndFreeze);
        ContentTweakerCore.LOGGER.info("Registries frozen");
    }

    private static <T> void registerAndFreeze(final Registry<T> registry) {
        registerFor(registry);
        registry.freeze();
    }

    private static <T> void registerFor(final Registry<T> registry) {
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
