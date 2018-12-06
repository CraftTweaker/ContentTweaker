package com.teamacronymcoders.contenttweaker.modules.vanilla.items;

import com.teamacronymcoders.contenttweaker.modules.vanilla.functions.IItemStackSupplier;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenRegister
@ZenClass("mods.contenttweaker.CreativeTab")
public interface ICreativeTab extends crafttweaker.api.creativetabs.ICreativeTab {
    @ZenGetter("unlocalizedName")
    String getUnlocalizedName();

    @ZenSetter("unlocalizedName")
    void setUnlocalizedName(String unlocalizedName);

    @ZenGetter("iconStack")
    IItemStack getIconStack();

    @ZenSetter("iconStack")
    void setIconStack(IItemStack iconStack);

    @ZenGetter("iconStackSupplier")
    IItemStackSupplier getIconStackSupplier();

    @ZenSetter("iconStackSupplier")
    void setIconStackSupplier(IItemStackSupplier stackSupplier);

    @ZenMethod
    void setHasSearch();

    @ZenMethod
    void register();
}
