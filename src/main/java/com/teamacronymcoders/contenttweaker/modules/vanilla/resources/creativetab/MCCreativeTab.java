package com.teamacronymcoders.contenttweaker.modules.vanilla.resources.creativetab;

import com.teamacronymcoders.contenttweaker.modules.vanilla.functions.IItemStackSupplier;
import com.teamacronymcoders.contenttweaker.modules.vanilla.items.ICreativeTab;
import crafttweaker.api.item.IItemStack;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class MCCreativeTab extends crafttweaker.mc1120.creativetabs.MCCreativeTab implements ICreativeTab {

    public MCCreativeTab(CreativeTabs creativeTabs) {
        super(creativeTabs, ReflectionHelper.getPrivateValue(CreativeTabs.class, creativeTabs, "tabLabel", "field_78034_o"));
    }

    @Override
    public String getUnlocalizedName() {
        return this.getTabLabel();
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
    public IItemStackSupplier getIconStackSupplier() {
        return this::getIconStack;
    }

    @Override
    public void setIconStackSupplier(IItemStackSupplier stackSupplier) {
        throw new IllegalStateException("Can't set Icon Stack for existing Creative Tab");
    }

    @Override
    public void setHasSearch() {

    }

    @Override
    public void register() {
        throw new IllegalStateException("Can't register an already existing Creative Tab");
    }
}
