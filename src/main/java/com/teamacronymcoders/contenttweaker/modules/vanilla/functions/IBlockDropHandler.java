package com.teamacronymcoders.contenttweaker.modules.vanilla.functions;


import com.teamacronymcoders.contenttweaker.api.ctobjects.blockstate.ICTBlockState;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.world.IBlockAccess;
import crafttweaker.api.world.IBlockPos;

import java.util.List;

public interface IBlockDropHandler {
    void handleDrops(List<IItemStack> drops, IBlockAccess world, IBlockPos pos, ICTBlockState state, int fortune);
}
