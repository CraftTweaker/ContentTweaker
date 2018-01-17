package com.teamacronymcoders.contenttweaker.modules.vanilla.functions;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;

import javax.annotation.Nullable;

@ZenClass("mods.contenttweaker.IItemGetContainerItem")
@ZenRegister
public interface IItemGetContainerItem {
    @Nullable
    IItemStack getContainerItem(IItemStack item);
}
