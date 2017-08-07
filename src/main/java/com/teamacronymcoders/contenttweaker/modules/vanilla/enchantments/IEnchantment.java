package com.teamacronymcoders.contenttweaker.modules.vanilla.enchantments;

import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.contenttweaker.Enchantment")
public interface IEnchantment {
    @ZenMethod
    String getIdentifier();

    @ZenMethod
    String getRarity();

    @ZenMethod
    int getMinLevel();

    @ZenMethod
    int getMaxLevel();

    @ZenMethod
    int getMinEnchantability(int enchantmentLevel);

    @ZenMethod
    int getMaxEnchantability(int enchantmentLevel);

    @ZenMethod
    String getName();

    @ZenMethod
    boolean canApply(IItemStack iItemStack);

    @ZenMethod
    boolean isTreasureEnchantment();

    @ZenMethod
    boolean canApplyAtEnchantingTable(IItemStack iItemStack);

    @ZenMethod
    boolean isAllowedOnBooks();

    Object getInternal();
}
