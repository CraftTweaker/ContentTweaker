package com.blamejared.contenttweaker.forge.registry;

import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.registry.GameRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryManager;

import java.util.Objects;

public final class VanillaGameRegistry<T> implements GameRegistry<T> {
    private final Registry<T> registry;
    private final ObjectType<T> type;

    private VanillaGameRegistry(final Registry<T> registry, final ObjectType<T> type) {
        this.registry = registry;
        this.type = type;
    }

    public static <T> VanillaGameRegistry<T> of(final ObjectType<T> type, final Registry<T> registry) {
        Objects.requireNonNull(registry, "registry");
        Objects.requireNonNull(type, "type");
        if (RegistryManager.ACTIVE.getRegistry(registry.key().location()) != null) {
            throw new IllegalArgumentException("Registry " + registry.key().location() + " is slave to a Forge Registry");
        }
        return new VanillaGameRegistry<>(registry, type);
    }

    @Override
    public ObjectType<T> type() {
        return this.type;
    }

    @Override
    public T get(final ResourceLocation name) {
        return this.registry.get(name);
    }

    @Override
    public void register(final ResourceLocation name, final T object) {
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(object, "object");
        Registry.register(this.registry, name, object);
    }

    @Override
    public String toString() {
        return "%s[%s]{%s}".formatted(this.registry.key().location(), this.type, this.registry);
    }
}
