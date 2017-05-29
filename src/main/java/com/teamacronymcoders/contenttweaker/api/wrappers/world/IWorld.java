package com.teamacronymcoders.contenttweaker.api.wrappers.world;

import com.teamacronymcoders.contenttweaker.api.wrappers.blockpos.IBlockPos;
import minetweaker.api.block.IBlock;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.contenttweaker.World")
public interface IWorld {
    @ZenMethod
    boolean isRemote();

    @ZenMethod
    boolean isRaining();

    @ZenMethod
    boolean isThundering();

    @ZenMethod
    boolean setBlockState(IBlock blockState, IBlockPos blockPos);

    Object getInternal();
}
