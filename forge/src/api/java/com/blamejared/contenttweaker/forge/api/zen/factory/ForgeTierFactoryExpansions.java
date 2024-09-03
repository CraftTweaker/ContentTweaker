package com.blamejared.contenttweaker.forge.api.zen.factory;

import com.blamejared.contenttweaker.core.api.ContentTweakerApi;
import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.action.RegisterObjectAction;
import com.blamejared.contenttweaker.core.api.object.ObjectHolder;
import com.blamejared.contenttweaker.core.api.resource.ResourceFragment;
import com.blamejared.contenttweaker.core.api.resource.StandardResourceFragmentKeys;
import com.blamejared.contenttweaker.forge.api.zen.ContentTweakerForgeConstants;
import com.blamejared.contenttweaker.forge.api.zen.rt.TierSortingStruct;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;
import com.blamejared.contenttweaker.vanilla.api.resource.PathHelper;
import com.blamejared.contenttweaker.vanilla.api.resource.Tag;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.factory.TierFactory;
import com.blamejared.contenttweaker.vanilla.api.zen.object.ItemReference;
import com.blamejared.contenttweaker.vanilla.api.zen.object.TierReference;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.core.registries.Registries;
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
import java.util.function.Function;
import java.util.function.Supplier;

@Document("mods/ContentTweaker/forge/factory/ExpandForgeTierFactory")
@ZenCodeType.Expansion(ContentTweakerVanillaConstants.VANILLA_FACTORY_PACKAGE + ".TierFactory")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class ForgeTierFactoryExpansions {
    private ForgeTierFactoryExpansions() {}

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
    public static TierReference create(
            @SuppressWarnings("unused") final TierFactory $this,
            final String name,
            final int level,
            final int uses,
            final float speed,
            final float attackDamageBonus,
            final int enchantmentValue,
            final ResourceLocation positiveFilterBlockTag,
            final ItemReference repairItem, // TODO("Figure out ingredients")
            @ZenCodeType.Optional(value = "[] as " + ContentTweakerForgeConstants.FORGE_RT_PACKAGE + ".TierSortingStruct[]") final TierSortingStruct[] lowerTiers,
            @ZenCodeType.Optional(value = "[] as " + ContentTweakerForgeConstants.FORGE_RT_PACKAGE + ".TierSortingStruct[]") final TierSortingStruct[] higherTiers
    ) {
        final ResourceLocation tierName = ContentTweakerConstants.rl(name);
        if (level < 0) {
            throw new IllegalArgumentException("Level for tier " + tierName + " cannot be negative");
        }
        if (uses <= 0) {
            throw new IllegalArgumentException("Uses for tier " + tierName + " cannot be negative or zero");
        }
        final TagKey<Block> tagKey = TagKey.create(Registries.BLOCK, positiveFilterBlockTag);

        final ObjectHolder<Tier> holder = ObjectHolder.of(VanillaObjectTypes.TIER, tierName, () -> {
            final Tier forgeTier = new ForgeTier(level, uses, speed, attackDamageBonus, enchantmentValue, tagKey, () -> Ingredient.of(repairItem.get()));
            final List<Object> resolvedLowerTiers = Arrays.stream(lowerTiers).map(TierSortingStruct::get).map(Supplier::get).toList();
            final List<Object> resolverHigherTiers = Arrays.stream(higherTiers).map(TierSortingStruct::get).map(Supplier::get).toList();
            return TierSortingRegistry.registerTier(forgeTier, tierName, resolvedLowerTiers, resolverHigherTiers);
        });
        ContentTweakerApi.apply(RegisterObjectAction.of(holder, manager -> {
            final ResourceFragment data = manager.fragment(StandardResourceFragmentKeys.CONTENT_TWEAKER_DATA);
            data.provideOrAlter(PathHelper.tag(VanillaObjectTypes.BLOCK, positiveFilterBlockTag), Tag::of, Function.identity(), Tag.SERIALIZER);
        }));

        return TierReference.of(tierName);
    }
}
