package com.blamejared.contenttweaker.fabric.api.zen.factory;

import com.blamejared.contenttweaker.core.api.ContentTweakerApi;
import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.action.RegisterObjectAction;
import com.blamejared.contenttweaker.core.api.object.ObjectHolder;
import com.blamejared.contenttweaker.fabric.api.util.CustomTier;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.factory.TierFactory;
import com.blamejared.contenttweaker.vanilla.api.zen.object.ItemReference;
import com.blamejared.contenttweaker.vanilla.api.zen.object.TierReference;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.util.NameUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;

@ZenCodeType.Expansion(ContentTweakerVanillaConstants.VANILLA_FACTORY_PACKAGE + ".TierFactory")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class FabricTierFactoryExpansions {
    private FabricTierFactoryExpansions() {}

    @ZenCodeType.Method
    public static TierReference of(
            @SuppressWarnings("unused") final TierFactory $this,
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

        final ObjectHolder<Tier> holder = ObjectHolder.of(
                VanillaObjectTypes.TIER,
                tierName,
                () -> CustomTier.of(uses, speed, attackDamageBonus, level, enchantmentValue, () -> Ingredient.of(repairItem.get()))
        );
        ContentTweakerApi.apply(RegisterObjectAction.of(holder));

        return TierReference.of(tierName);
    }

    private static void report(final String original, final String fixed, final List<String> mistakes) {
        CraftTweakerAPI.LOGGER.warn(() -> "The given name '%s' is not valid: it has been fixed to '%s'.\nMistakes:%s".formatted(
                original,
                fixed,
                String.join("\n", mistakes)
        ));
    }
}
