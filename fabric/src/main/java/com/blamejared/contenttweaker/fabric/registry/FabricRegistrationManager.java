package com.blamejared.contenttweaker.fabric.registry;

import com.blamejared.contenttweaker.core.ContentTweakerCore;
import com.blamejared.contenttweaker.core.api.ContentTweakerLoggers;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import net.minecraft.core.Registry;

public final class FabricRegistrationManager {
    private FabricRegistrationManager() {}

    public static void registerAll(final Registry<? extends Registry<?>> registry) {
        registry.iterator().forEachRemaining(FabricRegistrationManager::registerFor);
        ContentTweakerLoggers.core().info("Registration complete");
    }

    private static <T> void registerFor(final Registry<T> registry) {
        final ObjectType<T> type = ContentTweakerCore.core().metaRegistry().objectTypes().get(registry.key());
        if (type == null) {
            ContentTweakerLoggers.core().debug("Unknown registry '{}': are you missing object types for it?", registry.key());
            return;
        }
        ContentTweakerLoggers.core().info("Registering objects for type '{}'", type);
        try {
            FabricGameRegistry.of(registry, type).doRegistration();
        } catch (final Throwable e) {
            ContentTweakerLoggers.core().error("A critical internal ContentTweaker error occurred", e);
        }
    }
}
