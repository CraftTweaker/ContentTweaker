package com.teamacronymcoders.contenttweaker.modules.vanilla.items;

import com.teamacronymcoders.base.registrysystem.ItemRegistry;
import com.teamacronymcoders.contenttweaker.ContentTweaker;
import com.teamacronymcoders.contenttweaker.api.IRepresentation;
import com.teamacronymcoders.contenttweaker.modules.vanilla.functions.IItemRightClick;
import com.teamacronymcoders.contenttweaker.modules.vanilla.resources.creativetab.MCCreativeTab;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;

import java.util.Locale;

public class ItemRepresentation implements IRepresentation, IItem {
    private String unlocalizedName;
    private int maxStackSize = 64;
    private EnumRarity rarity = EnumRarity.COMMON;
    private CreativeTabs creativeTab = CreativeTabs.MISC;
    private float smeltingExperience = -1;
    private String toolClass = "";
    private int toolLevel = -1;
    private boolean beaconPayment = false;
    private IItemRightClick itemRightClick;

    private ItemContent itemContent;

    @Override
    public String getUnlocalizedName() {
        return unlocalizedName;
    }

    @Override
    public void setUnlocalizedName(String unlocalizedName) {
        this.unlocalizedName = unlocalizedName;
    }

    @Override
    public int getMaxStackSize() {
        return maxStackSize;
    }

    @Override
    public void setMaxStackSize(int maxStackSize) {
        this.maxStackSize = maxStackSize;
    }

    @Override
    public String getRarity() {
        return rarity.name();
    }

    public EnumRarity getInternalRarity() {
        return rarity;
    }

    @Override
    public void setRarity(String rarity) {
        this.rarity = EnumRarity.valueOf(rarity.toUpperCase(Locale.US));
    }

    @Override
    public ICreativeTab getCreativeTab() {
        return new MCCreativeTab(creativeTab);
    }

    public CreativeTabs getInternalCreativeTab() {
        return creativeTab;
    }

    @Override
    public void setCreativeTab(ICreativeTab creativeTab) {
        if (creativeTab.getInternal() instanceof CreativeTabs) {
            this.creativeTab = (CreativeTabs) creativeTab.getInternal();
        }
    }

    @Override
    public float getSmeltingExperience() {
        return smeltingExperience;
    }

    @Override
    public void setSmeltingExperience(float smeltingExperience) {
        this.smeltingExperience = smeltingExperience;
    }

    @Override
    public String getToolClass() {
        return toolClass;
    }

    @Override
    public void setToolClass(String toolClass) {
        this.toolClass = toolClass;
    }

    @Override
    public int getToolLevel() {
        return toolLevel;
    }

    @Override
    public void setToolLevel(int toolLevel) {
        this.toolLevel = toolLevel;
    }

    @Override
    public boolean isBeaconPayment() {
        return beaconPayment;
    }

    @Override
    public void setBeaconPayment(boolean beaconPayment) {
        this.beaconPayment = beaconPayment;
    }

    @Override
    public IItemRightClick getItemRightClick() {
        return this.itemRightClick;
    }

    @Override
    public void setItemRightClick(IItemRightClick itemRightClick) {
        this.itemRightClick = itemRightClick;
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
        this.itemContent = new ItemContent(this);
        ContentTweaker.instance.getRegistry(ItemRegistry.class, "ITEM").register(this.itemContent);
    }

    @Override
    public Object getInternal() {
        return this.itemContent;
    }
}
