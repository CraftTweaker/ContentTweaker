package com.blamejared.contenttweaker.forge.registry;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.google.common.base.Suppliers;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public final class ForgeGameRegistry<T extends IForgeRegistryEntry<T>> implements DeferredGameRegistry<T> {
    private static final Map<ObjectType<?>, ForgeGameRegistry<?>> INSTANCES = new HashMap<>();
    private static final Supplier<ModContainer> COT_CONTAINER = Suppliers.memoize(() -> ModList.get().getModContainerById(ContentTweakerConstants.MOD_ID).orElseThrow());

    private final ObjectType<T> type;
    private final ForgeRegistry<T> forgeRegistry;
    private final List<Runnable> commands;

    private ForgeGameRegistry(final ObjectType<T> type, final ForgeRegistry<T> registry) {
        this.type = type;
        this.forgeRegistry = registry;
        this.commands = new ArrayList<>();
    }

    static <T extends IForgeRegistryEntry<T>> ForgeGameRegistry<T> of(final ObjectType<T> type, final ForgeRegistry<T> registry) {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(registry, "registry");
        if (registry.getRegistrySuperType() != type.type()) {
            throw new IllegalArgumentException("Mismatched types between registry (" + registry.getRegistrySuperType().getName() + ") and type (" + type.type().getName() + ")");
        }
        return GenericUtil.uncheck(INSTANCES.computeIfAbsent(type, it -> new ForgeGameRegistry<>(type, registry)));
    }

    @Override
    public ObjectType<T> type() {
        return this.type;
    }

    @Override
    public T get(final ResourceLocation name) {
        return this.forgeRegistry.getValue(Objects.requireNonNull(name));
    }

    @Override
    public ResourceLocation nameOf(final T object) {
        return Objects.requireNonNull(object).getRegistryName();
    }

    @Override
    public void enqueueRegistration(final ResourceLocation name, final Supplier<T> object) {
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(object, "object");
        this.commands.add(() -> this.forgeRegistry.register(Objects.requireNonNull(object.get(), "get").setRegistryName(name)));
    }

    @Override
    public Collection<T> all() {
        return this.forgeRegistry.getValues();
    }

    @Override
    public List<Runnable> commands() {
        return this.commands;
    }

    @Override
    public void doRegistration() {
        this.withContainer(DeferredGameRegistry.super::doRegistration);
    }

    private void withContainer(final Runnable runnable) {
        final ModContainer activeContainer = ModLoadingContext.get().getActiveContainer();
        ModLoadingContext.get().setActiveContainer(COT_CONTAINER.get());
        runnable.run();
        ModLoadingContext.get().setActiveContainer(activeContainer);
    }

    @Override
    public String toString() {
        return "%s[%s]{%s}".formatted(this.forgeRegistry.getRegistryName(), this.type, this.forgeRegistry);
    }
}
