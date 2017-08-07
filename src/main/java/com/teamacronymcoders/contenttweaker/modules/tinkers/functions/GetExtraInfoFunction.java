package com.teamacronymcoders.contenttweaker.modules.tinkers.functions;

import crafttweaker.api.item.IItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface GetExtraInfoFunction {
    String[] getExtraInfo(IItemStack iItemStack, NBTTagCompound modifierTag);
}
