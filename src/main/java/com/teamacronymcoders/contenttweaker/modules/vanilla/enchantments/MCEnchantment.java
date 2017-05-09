package com.teamacronymcoders.contenttweaker.modules.vanilla.enchantments;

import minetweaker.api.item.IItemStack;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;

public class MCEnchantment implements IEnchantment {
    private Enchantment enchantment;

    public MCEnchantment(Enchantment enchantment) {
        this.enchantment = enchantment;
    }

    @Override
    public String getIdentifier() {
        return enchantment.getRegistryName().toString();
    }

    @Override
    public String getRarity() {
        return enchantment.getRarity().name();
    }

    @Override
    public int getMinLevel() {
        return enchantment.getMinLevel();
    }

    @Override
    public int getMaxLevel() {
        return enchantment.getMaxLevel();
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return enchantment.getMinEnchantability(enchantmentLevel);
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return enchantment.getMinEnchantability(enchantmentLevel);
    }

    @Override
    public String getName() {
        return enchantment.getName();
    }

    @Override
    public boolean canApply(IItemStack iItemStack) {
        return iItemStack.getInternal() instanceof ItemStack && enchantment.canApply((ItemStack) iItemStack.getInternal());
    }

    @Override
    public boolean isTreasureEnchantment() {
        return enchantment.isTreasureEnchantment();
    }

    @Override
    public boolean canApplyAtEnchantingTable(IItemStack iItemStack) {
        return iItemStack.getInternal() instanceof ItemStack && enchantment.canApplyAtEnchantingTable((ItemStack) iItemStack.getInternal());
    }

    @Override
    public boolean isAllowedOnBooks() {
        return enchantment.isAllowedOnBooks();
    }

    @Override
    public Object getInternal() {
        return enchantment;
    }
}
