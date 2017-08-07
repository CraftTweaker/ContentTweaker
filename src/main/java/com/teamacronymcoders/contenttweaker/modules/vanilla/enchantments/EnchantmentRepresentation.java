package com.teamacronymcoders.contenttweaker.modules.vanilla.enchantments;

import com.teamacronymcoders.contenttweaker.api.IRepresentation;
import crafttweaker.api.item.IItemStack;
import net.minecraft.item.EnumRarity;
import net.minecraft.util.ResourceLocation;

public class EnchantmentRepresentation implements IEditableEnchantment, IRepresentation {

    private ResourceLocation identifier;
    private EnumRarity rarity;
    private int minLevel;
    private int maxLevel;

    @Override
    public String getIdentifier() {
        return null;
    }

    @Override
    public String getRarity() {
        return null;
    }

    @Override
    public int getMinLevel() {
        return 0;
    }

    @Override
    public int getMaxLevel() {
        return 0;
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 0;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean canApply(IItemStack iItemStack) {
        return false;
    }

    @Override
    public boolean isTreasureEnchantment() {
        return false;
    }

    @Override
    public boolean canApplyAtEnchantingTable(IItemStack iItemStack) {
        return false;
    }

    @Override
    public boolean isAllowedOnBooks() {
        return false;
    }

    @Override
    public Object getInternal() {
        return null;
    }

    @Override
    public String getTypeName() {
        return null;
    }

    @Override
    public void register() {

    }
}
