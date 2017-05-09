package com.teamacronymcoders.contenttweaker.modules.tinkers.functions;

import minetweaker.api.item.IItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface GetExtraInfoFunction {
    String[] getExtraInfo(IItemStack iItemStack, NBTTagCompound modifierTag);
}
