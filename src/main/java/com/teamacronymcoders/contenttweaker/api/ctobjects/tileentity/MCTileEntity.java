package com.teamacronymcoders.contenttweaker.api.ctobjects.tileentity;

import com.teamacronymcoders.contenttweaker.api.ctobjects.blockpos.IBlockPos;
import com.teamacronymcoders.contenttweaker.api.ctobjects.blockpos.MCBlockPos;
import com.teamacronymcoders.contenttweaker.api.ctobjects.world.IWorld;
import com.teamacronymcoders.contenttweaker.api.ctobjects.world.MCWorld;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.data.IData;
import net.minecraft.tileentity.TileEntity;
import stanhebben.zenscript.annotations.ZenClass;

public class MCTileEntity implements IMCTileEntity {
    private final TileEntity tileEntity;

    public MCTileEntity(TileEntity tileEntity) {
        this.tileEntity = tileEntity;
    }

    @Override
    public IWorld getWorld() {
        return new MCWorld(tileEntity.getWorld());
    }

    @Override
    public IBlockPos getBlockPos() {
        return new MCBlockPos(tileEntity.getPos());
    }

    @Override
    public IData getTileData() {
        return tileEntity instanceof IAccessibleData ? ((IAccessibleData) tileEntity).getAccessibleData() : null;
    }

    @Override
    public TileEntity getInternal() {
        return tileEntity;
    }
}
