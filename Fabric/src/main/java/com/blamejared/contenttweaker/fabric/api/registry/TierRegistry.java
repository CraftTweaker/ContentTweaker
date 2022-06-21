package com.blamejared.contenttweaker.fabric.api.registry;

import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.registry.GameRegistry;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

// TODO("Allow mods to register tiers?")
public final class TierRegistry implements GameRegistry<Tier> {
    private final BiMap<ResourceLocation, Tier> knownTiers;
    private final BiMap<Tier, ResourceLocation> inverse;

    private TierRegistry(final Supplier<Map<ResourceLocation, Tier>> vanillaTiers) {
        this.knownTiers = HashBiMap.create(vanillaTiers.get());
        this.inverse = this.knownTiers.inverse();
    }

    public static TierRegistry of() {
        return new TierRegistry(TierRegistry::vanillaTiers);
    }

    private static Map<ResourceLocation, Tier> vanillaTiers() {
        return Arrays.stream(Tiers.values()).collect(Collectors.toMap(it -> new ResourceLocation(it.name().toLowerCase(Locale.ENGLISH)), Function.identity()));
    }

    @Override
    public ObjectType<Tier> type() {
        return VanillaObjectTypes.TIER;
    }

    @Override
    public Tier get(final ResourceLocation name) {
        return this.knownTiers.get(Objects.requireNonNull(name));
    }

    @Override
    public ResourceLocation nameOf(final Tier tier) {
        return this.inverse.computeIfAbsent(Objects.requireNonNull(tier), it -> {
            throw new IllegalStateException("No such tier with name " + it + " is known: either it does not exist or it was not found");
        });
    }

    @Override
    public Collection<Tier> all() {
        return this.knownTiers.values();
    }

    @Override
    public void enqueueRegistration(final ResourceLocation name, final Supplier<Tier> objectCreator) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(objectCreator);
        if (this.knownTiers.containsKey(name)) {
            throw new IllegalArgumentException("A tier with name '" + name + "' was already registered");
        }
        this.knownTiers.put(name, objectCreator.get());
    }
}
