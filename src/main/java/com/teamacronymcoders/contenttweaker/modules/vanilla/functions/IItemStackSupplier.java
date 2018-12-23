package com.teamacronymcoders.contenttweaker.modules.vanilla.functions;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("mods.contenttweaker.IItemStackSupplier")
public interface IItemStackSupplier {
    IItemStack getItemStack();
}
