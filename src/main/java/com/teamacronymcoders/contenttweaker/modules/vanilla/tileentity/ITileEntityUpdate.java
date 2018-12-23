package com.teamacronymcoders.contenttweaker.modules.vanilla.tileentity;

import com.teamacronymcoders.contenttweaker.api.ctobjects.blockpos.IBlockPos;
import com.teamacronymcoders.contenttweaker.api.ctobjects.world.IWorld;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.IData;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("mods.contenttweaker.TileEntityUpdate")
public interface ITileEntityUpdate {
    void onUpdate(IWorld world, IBlockPos blockPos, IData tileEntityData);
}
