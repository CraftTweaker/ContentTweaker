package com.teamacronymcoders.contenttweaker.api.wrappers.blockstate;

import minetweaker.api.block.IBlock;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.contenttweaker.BlockState")
public interface ICTBlockState {
    @ZenMethod
    IBlock getBlock();

    @ZenMethod
    int getMeta();

    Object getInternal();
}
