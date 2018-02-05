package com.teamacronymcoders.contenttweaker.modules.vanilla.resources.creativetab;

import com.teamacronymcoders.contenttweaker.modules.vanilla.items.ICreativeTab;
import crafttweaker.api.item.IItemStack;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class MCCreativeTab extends crafttweaker.mc1120.creativetabs.MCCreativeTab implements ICreativeTab {

    public MCCreativeTab(CreativeTabs creativeTabs) {
        super(creativeTabs, ReflectionHelper.getPrivateValue(CreativeTabs.class, creativeTabs, "tabLabel", "tabLabel"));
    }

    @Override
    public String getUnlocalizedName() {
        return this.getTabLabel();
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
    public void setHasSearch() {

    }

    @Override
    public void register() {

    }
}
