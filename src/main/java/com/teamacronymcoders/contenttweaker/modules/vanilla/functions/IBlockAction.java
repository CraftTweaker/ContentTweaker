package com.teamacronymcoders.contenttweaker.modules.vanilla.functions;

import com.teamacronymcoders.contenttweaker.api.wrappers.blockpos.IBlockPos;
import com.teamacronymcoders.contenttweaker.api.wrappers.blockstate.ICTBlockState;
import com.teamacronymcoders.contenttweaker.api.wrappers.world.IWorld;

public interface IBlockAction {
    void onBlockAction(IWorld world, IBlockPos blockPos, ICTBlockState blockState);
}
