package com.teamacronymcoders.contenttweaker.modules.vanilla.functions;

import com.teamacronymcoders.contenttweaker.api.ctobjects.blockpos.IBlockPos;
import com.teamacronymcoders.contenttweaker.api.ctobjects.blockstate.ICTBlockState;
import com.teamacronymcoders.contenttweaker.api.ctobjects.mutableitemstack.IMutableItemStack;
import com.teamacronymcoders.contenttweaker.api.ctobjects.world.IWorld;
import crafttweaker.api.entity.IEntityLivingBase;
import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("mods.contenttweaker.IItemDestroyedBlock")
public interface IItemDestroyedBlock {
    boolean onBlockDestroyed(IMutableItemStack stack, IWorld world, ICTBlockState state, IBlockPos pos, IEntityLivingBase entityLiving);
}
