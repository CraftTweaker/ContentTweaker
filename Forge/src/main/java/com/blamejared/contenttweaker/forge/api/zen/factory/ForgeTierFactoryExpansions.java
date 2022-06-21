package com.blamejared.contenttweaker.forge.api.zen.factory;

import com.blamejared.contenttweaker.core.api.ContentTweakerApi;
import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.action.RegisterObjectAction;
import com.blamejared.contenttweaker.core.api.object.ObjectHolder;
import com.blamejared.contenttweaker.core.api.resource.ResourceFragment;
import com.blamejared.contenttweaker.core.api.resource.StandardResourceFragmentKeys;
import com.blamejared.contenttweaker.forge.api.zen.rt.TierSortingStruct;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;
import com.blamejared.contenttweaker.vanilla.api.resource.PathHelper;
import com.blamejared.contenttweaker.vanilla.api.resource.Tag;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.object.ItemReference;
import com.blamejared.contenttweaker.vanilla.api.zen.object.TierReference;
import com.blamejared.contenttweaker.vanilla.zen.factory.TierFactory;
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
import java.util.function.Function;
import java.util.function.Supplier;

@ZenCodeType.Expansion(ContentTweakerVanillaConstants.VANILLA_FACTORY_PACKAGE + ".TierFactory")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class ForgeTierFactoryExpansions {
    private ForgeTierFactoryExpansions() {}

    @ZenCodeType.Method
    public static TierReference of(
            @SuppressWarnings("unused") final TierFactory $this,
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

        final ObjectHolder<Tier> holder = ObjectHolder.of(VanillaObjectTypes.TIER, tierName, () -> {
            final Tier forgeTier = new ForgeTier(level, uses, speed, attackDamageBonus, enchantmentValue, tagKey, () -> Ingredient.of(repairItem.get()));
            final List<Object> resolvedLowerTiers = Arrays.stream(lowerTiers).map(TierSortingStruct::get).map(Supplier::get).toList();
            final List<Object> resolverHigherTiers = Arrays.stream(higherTiers).map(TierSortingStruct::get).map(Supplier::get).toList();
            return TierSortingRegistry.registerTier(forgeTier, tierName, resolvedLowerTiers, resolverHigherTiers);
        });
        ContentTweakerApi.apply(RegisterObjectAction.of(holder, manager -> {
            final ResourceFragment data = manager.fragment(StandardResourceFragmentKeys.CONTENT_TWEAKER_DATA);
            data.provideOrAlter(PathHelper.tag(VanillaObjectTypes.BLOCK, tag), Tag::of, Function.identity(), Tag.SERIALIZER);
        }));

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
