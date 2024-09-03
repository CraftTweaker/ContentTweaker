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
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;

/**
 * Adds additional methods into {@link TierFactory}
 *
 * <pre><code class=language-zenscript>&lt;factory:tier&gt;.create(...);</code></pre>
 */
@Document("mods/ContentTweaker/fabric/factory/ExpandFabricTierFactory")
@ZenCodeType.Expansion(ContentTweakerVanillaConstants.VANILLA_FACTORY_PACKAGE + ".TierFactory")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class FabricTierFactoryExpansions {
    private FabricTierFactoryExpansions() {}

    /**
     * Create a new Tier
     *
     * @param $this self
     * @param name The name of the tier. Should match [a-z0-9.-_/], like most ResourceLocations
     * @param level The level of the tier. It is used for sorting. It is not possible to add a tier in between two existing ones.
     * @param uses The base durability an item on this tier has
     * @param speed The mining speed an item on this tier has
     * @param attackDamageBonus The attack bonus an item on this tier has
     * @param enchantmentValue The enchantability of the item. Higher is better.
     * @param repairItem The repair item. Must be an item and not a stack
     * @return A reference to the created tier
     *
     * @docParam $this this
     * @docParam name "emerald"
     * @docParam level 2
     * @docParam uses 581
     * @docParam speed 5.0f
     * @docParam attackDamageBonus 1.5
     * @docParam enchantmentValue 18
     * @docParam repairItem <item:minecraft:emerald>
     */
    @ZenCodeType.Method("create")
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
        final ResourceLocation tierName = ContentTweakerConstants.rl(name);
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
}
