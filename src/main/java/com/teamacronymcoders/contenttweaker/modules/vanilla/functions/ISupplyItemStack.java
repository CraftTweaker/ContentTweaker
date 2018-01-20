package com.teamacronymcoders.contenttweaker.modules.vanilla.functions;

import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("mods.contenttweaker.ISupplyItemStack")
public interface ISupplyItemStack {
    IItemStack get();
}
