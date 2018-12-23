package com.teamacronymcoders.contenttweaker.modules.vanilla.functions;

import com.teamacronymcoders.contenttweaker.api.ctobjects.blockstate.ICTBlockState;
import com.teamacronymcoders.contenttweaker.api.ctobjects.mutableitemstack.IMutableItemStack;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("mods.contenttweaker.IItemDestroySpeed")
public interface IItemDestroySpeed {
    float getDestroySpeed(IMutableItemStack itemStack, ICTBlockState blockState);
}
