package com.teamacronymcoders.contenttweaker.modules.vanilla.items;

import com.teamacronymcoders.base.registry.ItemRegistry;
import com.teamacronymcoders.contenttweaker.ContentTweaker;
import com.teamacronymcoders.contenttweaker.api.IRepresentation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;

public class ItemRepresentation implements IRepresentation, IItem {
    private String unlocalizedName;
    private int maxStackSize = 64;
    private EnumRarity rarity = EnumRarity.COMMON;
    private CreativeTabs creativeTab = CreativeTabs.MISC;
    private float smeltingExperience = -1;
    private String toolClass = "";
    private int toolLevel = -1;
    private boolean beaconPayment = false;

    public String getUnlocalizedName() {
        return unlocalizedName;
    }

    public void setUnlocalizedName(String unlocalizedName) {
        this.unlocalizedName = unlocalizedName;
    }

    public int getMaxStackSize() {
        return maxStackSize;
    }

    public void setMaxStackSize(int maxStackSize) {
        this.maxStackSize = maxStackSize;
    }

    public EnumRarity getRarity() {
        return rarity;
    }

    public void setRarity(EnumRarity rarity) {
        this.rarity = rarity;
    }

    public CreativeTabs getCreativeTab() {
        return creativeTab;
    }

    public void setCreativeTab(CreativeTabs creativeTab) {
        this.creativeTab = creativeTab;
    }

    public float getSmeltingExperience() {
        return smeltingExperience;
    }

    public void setSmeltingExperience(float smeltingExperience) {
        this.smeltingExperience = smeltingExperience;
    }

    public String getToolClass() {
        return toolClass;
    }

    public void setToolClass(String toolClass) {
        this.toolClass = toolClass;
    }

    public int getToolLevel() {
        return toolLevel;
    }

    public void setToolLevel(int toolLevel) {
        this.toolLevel = toolLevel;
    }

    public boolean isBeaconPayment() {
        return beaconPayment;
    }

    public void setBeaconPayment(boolean beaconPayment) {
        this.beaconPayment = beaconPayment;
    }

    @Override
    public String getName() {
        return this.getUnlocalizedName();
    }

    @Override
    public String getTypeName() {
        return "Item";
    }

    @Override
    public void register() {
        ContentTweaker.instance.getRegistry(ItemRegistry.class, "ITEM").register(new ItemContent(this));
    }
}
