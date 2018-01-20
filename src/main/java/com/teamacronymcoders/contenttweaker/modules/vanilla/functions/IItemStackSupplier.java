package com.teamacronymcoders.contenttweaker.modules.vanilla.functions;

import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("mods.contenttweaker.IItemStackSupplier")
public interface IItemStackSupplier {
    IItemStack get();
}
