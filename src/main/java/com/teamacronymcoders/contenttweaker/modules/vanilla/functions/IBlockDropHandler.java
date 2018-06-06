package com.teamacronymcoders.contenttweaker.modules.vanilla.functions;


import com.teamacronymcoders.contenttweaker.api.ctobjects.blockstate.ICTBlockState;
import com.teamacronymcoders.contenttweaker.api.ctobjects.itemlist.ICTItemList;
import crafttweaker.api.world.IBlockAccess;
import crafttweaker.api.world.IBlockPos;
import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("mods.contenttweaker.DropHandler")
public interface IBlockDropHandler {
    void handleDrops(ICTItemList drops, IBlockAccess world, IBlockPos pos, ICTBlockState state, int fortune);
}
