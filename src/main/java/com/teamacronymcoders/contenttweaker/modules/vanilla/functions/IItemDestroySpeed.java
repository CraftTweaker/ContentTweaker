package com.teamacronymcoders.contenttweaker.modules.vanilla.functions;

import com.teamacronymcoders.contenttweaker.api.ctobjects.blockstate.ICTBlockState;
import com.teamacronymcoders.contenttweaker.api.ctobjects.mutableitemstack.IMutableItemStack;

public interface IItemDestroySpeed {
    float getDestroySpeed(IMutableItemStack itemStack, ICTBlockState blockState);
}
