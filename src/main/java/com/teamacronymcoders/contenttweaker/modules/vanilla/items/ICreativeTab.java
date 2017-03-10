package com.teamacronymcoders.contenttweaker.modules.vanilla.items;

import minetweaker.api.item.IItemStack;

public interface ICreativeTab {
    String getUnlocalizedName();

    void setUnlocalizedName(String unlocalizedName);

    IItemStack getIconStack();

    void setIconStack(IItemStack iconStack);
}
