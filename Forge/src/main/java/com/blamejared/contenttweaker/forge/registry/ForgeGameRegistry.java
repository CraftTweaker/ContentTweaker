package com.blamejared.contenttweaker.forge.registry;

import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.registry.GameRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Objects;

public final class ForgeGameRegistry<T extends IForgeRegistryEntry<T>> implements GameRegistry<T> {
    private final ObjectType<T> type;
    private final ForgeRegistry<T> forgeRegistry;

    private ForgeGameRegistry(final ObjectType<T> type, final ForgeRegistry<T> registry) {
        this.type = type;
        this.forgeRegistry = registry;
    }

    public static <T extends IForgeRegistryEntry<T>> ForgeGameRegistry<T> of(final ObjectType<T> type, final ForgeRegistry<T> registry) {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(registry, "registry");
        if (registry.getRegistrySuperType() != type.type()) {
            throw new IllegalArgumentException("Mismatched types between registry (" + registry.getRegistrySuperType().getName() + ") and type (" + type.type().getName() + ")");
        }
        return new ForgeGameRegistry<>(type, registry);
    }

    @Override
    public ObjectType<T> type() {
        return this.type;
    }

    @Override
    public T get(final ResourceLocation name) {
        return this.forgeRegistry.getValue(name);
    }

    @Override
    public void register(final ResourceLocation name, final T object) {
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(object, "object");
        this.forgeRegistry.register(object.setRegistryName(name));
    }

    @Override
    public String toString() {
        return "%s[%s]{%s}".formatted(this.forgeRegistry.getRegistryName(), this.type, this.forgeRegistry);
    }
}
