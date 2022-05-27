package com.blamejared.contenttweaker.fabric.util;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class ContentTweakerTierRegistry {
    private static final Supplier<ContentTweakerTierRegistry> INSTANCE = Suppliers.memoize(
            () -> new ContentTweakerTierRegistry(ContentTweakerTierRegistry::vanillaTiers, ContentTweakerTierRegistry::vanillaLevels)
    );

    private final BiMap<ResourceLocation, Tier> knownTiers;
    private final BiMap<Tier, ResourceLocation> inverse;
    private final Int2ObjectMap<Tier> tiersByLevel;

    private ContentTweakerTierRegistry(
            final Supplier<Map<ResourceLocation, Tier>> vanillaTiers,
            final Function<Map<ResourceLocation, Tier>, Map<Integer, Tier>> tierCreator
    ) {
        this.knownTiers = HashBiMap.create(vanillaTiers.get());
        this.inverse = this.knownTiers.inverse();
        this.tiersByLevel = new Int2ObjectArrayMap<>(tierCreator.apply(this.knownTiers));
    }

    public static ContentTweakerTierRegistry of() {
        return INSTANCE.get();
    }

    private static Map<ResourceLocation, Tier> vanillaTiers() {
        return Arrays.stream(Tiers.values()).collect(Collectors.toMap(it -> new ResourceLocation(it.name().toLowerCase(Locale.ENGLISH)), Function.identity()));
    }

    private static Map<Integer, Tier> vanillaLevels(final Map<ResourceLocation, Tier> tiers) {
        return tiers.values().stream().collect(Collectors.toMap(Tier::getLevel, Function.identity()));
    }

    public Tier create(
            final ResourceLocation name,
            final int level,
            final int uses,
            final float speed,
            final float attackDamageBonus,
            final int enchantmentValue,
            final Supplier<Ingredient> repairItem
    ) {
        if (this.knownTiers.containsKey(name)) {
            throw new IllegalArgumentException("Tier already exists: " + name);
        }
        final Tier tier = new ContentTweakerTier(uses, speed, attackDamageBonus, level, enchantmentValue, Objects.requireNonNull(repairItem));
        this.knownTiers.put(name, tier);
        if (!this.tiersByLevel.containsKey(level)) { // In Fabric, all tiers with the same level are the same
            this.tiersByLevel.put(level, tier);
        }
        return tier;
    }

    public ResourceLocation nameOf(final Tier tier) {
        return this.inverse.computeIfAbsent(Objects.requireNonNull(tier), it -> {
            throw new IllegalStateException("No such tier with name " + it + " is known: either it does not exist or it was not found");
        });
    }

    public Tier ofLevel(final int level) {
        return this.tiersByLevel.get(level);
    }
}
