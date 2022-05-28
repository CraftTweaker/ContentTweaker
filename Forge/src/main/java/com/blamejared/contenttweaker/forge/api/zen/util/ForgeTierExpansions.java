package com.blamejared.contenttweaker.forge.api.zen.util;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.forge.api.zen.rt.TierSortingStruct;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.object.ItemReference;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.util.NameUtil;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ZenCodeType.Expansion(ContentTweakerVanillaConstants.VANILLA_UTIL_PACKAGE + ".Tier")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class ForgeTierExpansions {

    private ForgeTierExpansions() {}

    @ZenCodeType.StaticExpansionMethod
    public static Tier of(
            final String name,
            final int level,
            final int uses,
            final float speed,
            final float attackDamageBonus,
            final int enchantmentValue,
            final ResourceLocation tag,
            final ItemReference repairItem, // TODO("Figure out ingredients")
            @ZenCodeType.Optional(value = "[] as " + ContentTweakerVanillaConstants.VANILLA_RT_PACKAGE + ".TierSortingStruct[]") final TierSortingStruct[] lowerTiers,
            @ZenCodeType.Optional(value = "[] as " + ContentTweakerVanillaConstants.VANILLA_RT_PACKAGE + ".TierSortingStruct[]") final TierSortingStruct[] higherTiers
    ) {
        final ResourceLocation tierName = ContentTweakerConstants.rl(NameUtil.fixing(name, (fixed, mistakes) -> report(name, fixed, mistakes)));
        if (level < 0) {
            throw new IllegalArgumentException("Level for tier " + tierName + " cannot be negative");
        }
        if (uses <= 0) {
            throw new IllegalArgumentException("Uses for tier " + tierName + " cannot be negative or zero");
        }
        // TODO("Additional checks?")
        final TagKey<Block> tagKey = TagKey.create(Registry.BLOCK_REGISTRY, tag);
        final Tier tier = new ForgeTier(level, uses, speed, attackDamageBonus, enchantmentValue, tagKey, () -> Ingredient.of(repairItem.get()));
        final List<Object> lowerTiersList = Arrays.stream(lowerTiers).map(TierSortingStruct::get).toList();
        final List<Object> higherTiersList = Arrays.stream(higherTiers).map(TierSortingStruct::get).toList();
        return TierSortingRegistry.registerTier(tier, tierName, lowerTiersList, higherTiersList);
    }

    private static void report(final String original, final String fixed, final List<String> mistakes) {
        CraftTweakerAPI.LOGGER.warn(() -> "The given name '%s' is not valid: it has been fixed to '%s'.\nMistakes:%s".formatted(
                original,
                fixed,
                String.join("\n", mistakes)
        ));
    }

    @ZenCodeType.Getter("name")
    @ZenCodeType.Nullable
    public static ResourceLocation name(final Tier $this) {
        return TierSortingRegistry.getName($this);
    }

    @ZenCodeType.Getter("tag")
    @ZenCodeType.Nullable
    public static ResourceLocation tag(final Tier $this) {
        final TagKey<Block> tag = $this.getTag();
        if (tag != null) {
            return tag.location();
        }
        return null;
    }

    @ZenCodeType.Getter("lowerTiers")
    public static Tier[] lowerTiers(final Tier $this) {
        return TierSortingRegistry.getTiersLowerThan($this).toArray(Tier[]::new);
    }

    @ZenCodeType.Getter("higherTiers")
    public static Tier[] higherTiers(final Tier $this) {
        final List<Tier> allTiers = new ArrayList<>(TierSortingRegistry.getSortedTiers());
        allTiers.removeAll(TierSortingRegistry.getTiersLowerThan($this));
        allTiers.remove($this);
        return allTiers.toArray(Tier[]::new);
    }
}
