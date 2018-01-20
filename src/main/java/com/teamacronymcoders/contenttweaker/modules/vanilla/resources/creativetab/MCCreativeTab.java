package com.teamacronymcoders.contenttweaker.modules.vanilla.resources.creativetab;

import com.teamacronymcoders.contenttweaker.modules.vanilla.functions.ISupplyItemStack;
import com.teamacronymcoders.contenttweaker.modules.vanilla.items.ICreativeTab;
import crafttweaker.api.item.IItemStack;
import net.minecraft.creativetab.CreativeTabs;

public class MCCreativeTab implements ICreativeTab {
    private CreativeTabs creativeTabs;

    public MCCreativeTab(CreativeTabs creativeTabs) {
        this.creativeTabs = creativeTabs;
    }

    @Override
    public String getUnlocalizedName() {
        return null;
    }

    @Override
    public void setUnlocalizedName(String unlocalizedName) {
        throw new IllegalStateException("Can't set Unlocalized name for existing Creative Tab");
    }

    @Override
    public IItemStack getIconStack() {
        return null;
    }

    @Override
    public void setIconStack(IItemStack iconStack) {
        setIconStackSupplier(() -> iconStack);
    }

    @Override
    public ISupplyItemStack getIconStackSupplier() {
        return this::getIconStack;
    }

    @Override
    public void setIconStackSupplier(ISupplyItemStack stackSupplier) {
        throw new IllegalStateException("Can't set Icon Stack for existing Creative Tab");
    }

    @Override
    public void register() {
        throw new IllegalStateException("Can't register an already existing Creative Tab");
    }

    @Override
    public CreativeTabs getInternal() {
        return this.creativeTabs;
    }
}
