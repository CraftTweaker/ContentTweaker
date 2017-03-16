package com.teamacronymcoders.contenttweaker.modules.vanilla.items;

import minetweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.contenttweaker.CreativeTab")
public interface ICreativeTab {
    @ZenMethod
    String getUnlocalizedName();

    @ZenMethod
    void setUnlocalizedName(String unlocalizedName);

    @ZenMethod
    IItemStack getIconStack();

    @ZenMethod
    void setIconStack(IItemStack iconStack);

    @ZenMethod
    Object getInternal();
}
