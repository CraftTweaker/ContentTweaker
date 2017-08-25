package com.teamacronymcoders.contenttweaker.modules.vanilla.events;

import com.teamacronymcoders.contenttweaker.api.ctobjects.inventory.ICTInventory;
import crafttweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;

@FunctionalInterface
@ZenClass("mods.contenttweaker.OnItemCraft")
public interface OnItemCraft {
    void onCrafted(IItemStack itemStack, ICTInventory inventory);
}
