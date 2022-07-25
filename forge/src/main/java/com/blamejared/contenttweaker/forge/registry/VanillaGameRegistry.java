package com.blamejared.contenttweaker.forge.registry;

import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

public final class VanillaGameRegistry<T> implements DeferredGameRegistry<T> {
    private static final Map<ObjectType<?>, VanillaGameRegistry<?>> INSTANCES = new HashMap<>();

    private final Registry<T> registry;
    private final ObjectType<T> type;
    private final List<Runnable> commands;

    private VanillaGameRegistry(final Registry<T> registry, final ObjectType<T> type) {
        this.registry = registry;
        this.type = type;
        this.commands = new ArrayList<>();
    }

    static <T> VanillaGameRegistry<T> of(final ObjectType<T> type, final Registry<T> registry) {
        Objects.requireNonNull(registry, "registry");
        Objects.requireNonNull(type, "type");
        if (RegistryManager.ACTIVE.getRegistry(registry.key().location()) != null) {
            throw new IllegalArgumentException("Registry " + registry.key().location() + " is slave to a Forge Registry");
        }
        return GenericUtil.uncheck(INSTANCES.computeIfAbsent(type, it -> new VanillaGameRegistry<>(registry, type)));
    }

    @Override
    public ObjectType<T> type() {
        return this.type;
    }

    @Override
    public T get(final ResourceLocation name) {
        return this.registry.get(Objects.requireNonNull(name));
    }

    @Override
    public ResourceLocation nameOf(final T object) {
        return this.registry.getKey(Objects.requireNonNull(object));
    }

    @Override
    public Collection<T> all() {
        return this.stream().toList();
    }

    @Override
    public void enqueueRegistration(final ResourceLocation name, final Supplier<T> object) {
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(object, "object");
        this.commands.add(() -> Registry.register(this.registry, name, Objects.requireNonNull(object.get(), "get")));
    }

    @Override
    public Stream<T> stream() {
        return this.registry.stream();
    }

    @Override
    public List<Runnable> commands() {
        return this.commands;
    }

    @Override
    public String toString() {
        return "%s[%s]{%s}".formatted(this.registry.key().location(), this.type, this.registry);
    }
}
