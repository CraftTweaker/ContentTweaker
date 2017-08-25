package com.teamacronymcoders.contenttweaker.api.ctobjects.blockpos;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.contenttweaker.BlockPos")
public interface IBlockPos {
    @ZenMethod
    int getX();

    @ZenMethod
    int getY();

    @ZenMethod
    int getZ();

    @ZenMethod
    IBlockPos getOffset(String directionName, int offset);

    Object getInternal();
}
