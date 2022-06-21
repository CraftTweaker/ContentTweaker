package com.blamejared.contenttweaker.forge.api.registry;

import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.registry.GameRegistry;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public final class TierRegistry implements GameRegistry<Tier> {
    private final Map<ResourceLocation, Supplier<Tier>> commands;

    private TierRegistry() {
        this.commands = new HashMap<>();
    }

    public static TierRegistry of() {
        return new TierRegistry();
    }

    @Override
    public ObjectType<Tier> type() {
        return VanillaObjectTypes.TIER;
    }

    @Override
    public Tier get(final ResourceLocation name) {
        this.flushCommands();
        return TierSortingRegistry.byName(Objects.requireNonNull(name));
    }

    @Override
    public ResourceLocation nameOf(final Tier object) {
        this.flushCommands();
        return TierSortingRegistry.getName(Objects.requireNonNull(object));
    }

    @Override
    public Collection<Tier> all() {
        this.flushCommands();
        return TierSortingRegistry.getSortedTiers();
    }

    @Override
    public void enqueueRegistration(final ResourceLocation name, final Supplier<Tier> objectCreator) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(objectCreator);
        if (this.commands.containsKey(name)) {
            throw new IllegalStateException("A tier with the name '" + name + "' was already registered");
        }
        this.commands.put(name, objectCreator);
    }

    private void flushCommands() {
        // Due to how Forge works, we need to encode information directly in the supplier: due to this, calling the
        // supplier automatically registers the tier
        if (this.commands.isEmpty()) {
            return;
        }

        this.commands.forEach((name, value) -> value.get());
        this.commands.clear();
    }
}
