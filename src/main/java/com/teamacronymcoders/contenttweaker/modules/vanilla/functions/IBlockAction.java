package com.teamacronymcoders.contenttweaker.modules.vanilla.functions;

import com.teamacronymcoders.contenttweaker.api.ctobjects.blockpos.IBlockPos;
import com.teamacronymcoders.contenttweaker.api.ctobjects.blockstate.ICTBlockState;
import com.teamacronymcoders.contenttweaker.api.ctobjects.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("mods.contenttweaker.IBlockAction")
public interface IBlockAction {
    void onBlockAction(IWorld world, IBlockPos blockPos, ICTBlockState blockState);
}
