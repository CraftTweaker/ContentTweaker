package com.blamejared.contenttweaker.forge.registry;

import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class GameRegistryFactory {
    private static final Map<ObjectType<?>, DeferredGameRegistry<?>> REGISTRY_CACHE = new HashMap<>();

    private GameRegistryFactory() {}

    public static <T, U> DeferredGameRegistry<T> findRegistryFromTypeAlone(final ObjectType<T> type) {
        Objects.requireNonNull(type);
        return GenericUtil.uncheck(REGISTRY_CACHE.computeIfAbsent(type, it -> findRegistry(type, type.key())));
    }

    public static <T> DeferredGameRegistry<T> findRegistryFromKey(final ObjectType<T> type, final ResourceKey<? extends Registry<T>> key) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(key);
        return GenericUtil.uncheck(REGISTRY_CACHE.computeIfAbsent(type, it -> findRegistry(type, key)));
    }

    private static <T> DeferredGameRegistry<T> findRegistry(final ObjectType<T> type, final ResourceKey<? extends Registry<T>> key) {
        final ResourceLocation registryId = Objects.requireNonNull(key).location();

        // If this is a Forge registry, we need to use it because of Forge shenanigans and to avoid being limited to a slave
        // If the registry is not a Forge registry, we can fall back to Vanilla
        return Objects.requireNonNullElseGet(findForgeRegistry(type, registryId), () -> findVanillaRegistry(type, registryId));
    }

    private static <T, U extends IForgeRegistryEntry<U>> DeferredGameRegistry<T> findForgeRegistry(final ObjectType<T> type, final ResourceLocation registryId) {
        // Forge wants all types to implement IForgeRegistryEntry, so we need to check that
        if (!IForgeRegistryEntry.class.isAssignableFrom(type.type())) {
            return null;
        }

        // The type definitely extends IForgeRegistryEntry now, so we'll perform an unchecked cast and hope for the best
        final DeferredGameRegistry<U> captured = findForgeRegistryCapturing(GenericUtil.uncheck(type), registryId);
        return captured == null? null : GenericUtil.uncheck(captured);
    }

    private static <T extends IForgeRegistryEntry<T>> DeferredGameRegistry<T> findForgeRegistryCapturing(final ObjectType<T> type, final ResourceLocation registryId) {
        final ForgeRegistry<T> registry = RegistryManager.ACTIVE.getRegistry(registryId);
        if (registry == null) {
            return null;
        }
        return ForgeGameRegistry.of(type, registry);
    }

    private static <T> DeferredGameRegistry<T> findVanillaRegistry(final ObjectType<T> type, final ResourceLocation registryId) {
        final Registry<T> registry = GenericUtil.uncheck(Registry.REGISTRY.get(registryId));
        return VanillaGameRegistry.of(type, registry);
    }
}
