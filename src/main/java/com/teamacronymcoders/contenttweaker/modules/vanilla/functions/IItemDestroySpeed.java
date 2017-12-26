package com.teamacronymcoders.contenttweaker.modules.vanilla.functions;

import com.teamacronymcoders.contenttweaker.api.ctobjects.blockstate.ICTBlockState;
import com.teamacronymcoders.contenttweaker.api.ctobjects.mutableitemstack.IMutableItemStack;
import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("mods.contenttweaker.IItemDestroySpeed")
public interface IItemDestroySpeed {
    float getDestroySpeed(IMutableItemStack itemStack, ICTBlockState blockState);
}
