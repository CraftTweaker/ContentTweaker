package com.teamacronymcoders.contenttweaker.modules.vanilla.functions;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("mods.contenttweaker.IItemCrafted")
@ZenRegister
public interface IItemCrafted {
    IItemStack getContainerItem(IItemStack item);
}
