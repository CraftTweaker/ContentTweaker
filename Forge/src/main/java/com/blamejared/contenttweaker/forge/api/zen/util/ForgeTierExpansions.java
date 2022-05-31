package com.blamejared.contenttweaker.forge.api.zen.util;

import com.blamejared.contenttweaker.core.api.ContentTweakerApi;
import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.forge.api.zen.rt.TierSortingStruct;
import com.blamejared.contenttweaker.vanilla.api.action.tier.CreateTierAction;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.object.ItemReference;
import com.blamejared.contenttweaker.vanilla.api.zen.util.TierReference;
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

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

@ZenCodeType.Expansion(ContentTweakerVanillaConstants.VANILLA_UTIL_PACKAGE + ".TierReference")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class ForgeTierExpansions {

    private ForgeTierExpansions() {}

    @ZenCodeType.StaticExpansionMethod
    public static TierReference of(
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
        final List<Supplier<Object>> lowerTiersList = Arrays.stream(lowerTiers).map(TierSortingStruct::get).toList();
        final List<Supplier<Object>> higherTiersList = Arrays.stream(higherTiers).map(TierSortingStruct::get).toList();
        return TierReference.of(tierName, level, self -> of(self, tierName, level, uses, speed, attackDamageBonus, enchantmentValue, tagKey, repairItem, lowerTiersList, higherTiersList));
    }

    private static void report(final String original, final String fixed, final List<String> mistakes) {
        CraftTweakerAPI.LOGGER.warn(() -> "The given name '%s' is not valid: it has been fixed to '%s'.\nMistakes:%s".formatted(
                original,
                fixed,
                String.join("\n", mistakes)
        ));
    }

    private static Tier of(
            final TierReference self,
            final ResourceLocation name,
            final int level,
            final int uses,
            final float speed,
            final float attack,
            final int enchantment,
            final TagKey<Block> tag,
            final ItemReference repairItem,
            final List<Supplier<Object>> lowerTiers,
            final List<Supplier<Object>> higherTiers
    ) {
        final Tier[] tier = new Tier[1];
        ContentTweakerApi.apply(CreateTierAction.of(self, () -> {
            final Tier forgeTier = new ForgeTier(level, uses, speed, attack, enchantment, tag, () -> Ingredient.of(repairItem.get()));
            final List<Object> lowerTiersList = lowerTiers.stream().map(Supplier::get).toList();
            final List<Object> higherTiersList = higherTiers.stream().map(Supplier::get).toList();
            tier[0] = TierSortingRegistry.registerTier(forgeTier, name, lowerTiersList, higherTiersList);
        }));
        return tier[0];
    }

    /*
    @ZenCodeType.Getter("lowerTiers")
    public static Tier[] lowerTiers(final TierReference $this) {
        return TierSortingRegistry.getTiersLowerThan($this.unwrap()).toArray(Tier[]::new);
    }

    @ZenCodeType.Getter("higherTiers")
    public static Tier[] higherTiers(final TierReference $this) {
        final List<Tier> allTiers = new ArrayList<>(TierSortingRegistry.getSortedTiers());
        allTiers.removeAll(TierSortingRegistry.getTiersLowerThan($this.unwrap()));
        allTiers.remove($this.unwrap());
        return allTiers.toArray(Tier[]::new);
    }
     */
}
