package com.teamacronymcoders.contenttweaker.modules.vanilla.resources.creativetab;

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

    }

    @Override
    public IItemStack getIconStack() {
        return null;
    }

    @Override
    public void setIconStack(IItemStack iconStack) {

    }

    @Override
    public void register() {

    }

    @Override
    public CreativeTabs getInternal() {
        return this.creativeTabs;
    }
}
