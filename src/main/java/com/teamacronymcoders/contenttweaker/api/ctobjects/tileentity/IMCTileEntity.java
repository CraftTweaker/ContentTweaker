package com.teamacronymcoders.contenttweaker.api.ctobjects.tileentity;

import com.teamacronymcoders.contenttweaker.api.ICTObject;
import com.teamacronymcoders.contenttweaker.api.ctobjects.blockpos.IBlockPos;
import com.teamacronymcoders.contenttweaker.api.ctobjects.world.IWorld;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.IData;
import net.minecraft.tileentity.TileEntity;
import stanhebben.zenscript.annotations.ZenClass;

public interface IMCTileEntity extends ICTObject<TileEntity> {
    IWorld getWorld();

    IBlockPos getBlockPos();

    IData getTileData();
}
