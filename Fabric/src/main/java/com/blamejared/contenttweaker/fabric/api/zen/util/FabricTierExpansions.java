package com.blamejared.contenttweaker.fabric.api.zen.util;

import com.blamejared.contenttweaker.core.api.ContentTweakerApi;
import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.fabric.api.util.ContentTweakerTierRegistry;
import com.blamejared.contenttweaker.vanilla.api.action.tier.CreateTierAction;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.object.ItemReference;
import com.blamejared.contenttweaker.vanilla.api.zen.util.TierReference;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.util.NameUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;

@ZenCodeType.Expansion(ContentTweakerVanillaConstants.VANILLA_UTIL_PACKAGE + ".TierReference")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class FabricTierExpansions {
    private FabricTierExpansions() {}

    @ZenCodeType.StaticExpansionMethod
    public static TierReference of(
            final String name,
            final int level,
            final int uses,
            final float speed,
            final float attackDamageBonus,
            final int enchantmentValue,
            final ItemReference repairItem // TODO("Figure out ingredients")
    ) {
        final ResourceLocation tierName = ContentTweakerConstants.rl(NameUtil.fixing(name, (fixed, mistakes) -> report(name, fixed, mistakes)));
        if (level < 0) {
            throw new IllegalArgumentException("Level for tier " + tierName + " cannot be negative");
        }
        if (uses <= 0) {
            throw new IllegalArgumentException("Uses for tier " + tierName + " cannot be negative or zero");
        }
        // TODO("Additional checks?")
        return TierReference.of(
                tierName,
                level,
                self -> make(self, tierName, level, uses, speed, attackDamageBonus, enchantmentValue, repairItem)
        );
    }

    private static void report(final String original, final String fixed, final List<String> mistakes) {
        CraftTweakerAPI.LOGGER.warn(() -> "The given name '%s' is not valid: it has been fixed to '%s'.\nMistakes:%s".formatted(
                original,
                fixed,
                String.join("\n", mistakes)
        ));
    }

    private static Tier make(
            final TierReference reference,
            final ResourceLocation name,
            final int level,
            final int uses,
            final float speed,
            final float attack,
            final int enchantment,
            final ItemReference repairIngredient
    ) {
        final Tier[] tier = new Tier[1];
        final ContentTweakerTierRegistry registry = ContentTweakerTierRegistry.of();
        ContentTweakerApi.apply(CreateTierAction.of(reference, () -> tier[0] = registry.create(name, level, uses, speed, attack, enchantment, () -> Ingredient.of(repairIngredient.get()))));
        return tier[0];
    }
}
