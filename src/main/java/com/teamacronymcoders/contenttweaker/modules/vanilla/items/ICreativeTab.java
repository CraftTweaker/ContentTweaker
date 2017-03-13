package com.teamacronymcoders.contenttweaker.modules.vanilla.items;

import com.teamacronymcoders.contenttweaker.modules.vanilla.resources.creativetab.ICreativeTabDefinition;
import minetweaker.api.item.IItemStack;

public interface ICreativeTab extends ICreativeTabDefinition {
    String getUnlocalizedName();

    void setUnlocalizedName(String unlocalizedName);

    IItemStack getIconStack();

    void setIconStack(IItemStack iconStack);
}
