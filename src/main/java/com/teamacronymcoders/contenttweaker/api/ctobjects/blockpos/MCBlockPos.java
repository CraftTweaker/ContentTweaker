package com.teamacronymcoders.contenttweaker.api.ctobjects.blockpos;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class MCBlockPos implements IBlockPos {
    private BlockPos blockPos;

    public MCBlockPos(BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    @Override
    public int getX() {
        return blockPos.getX();
    }

    @Override
    public int getY() {
        return blockPos.getY();
    }

    @Override
    public int getZ() {
        return blockPos.getZ();
    }

    @Override
    public IBlockPos getOffset(String directionName, int offset) {
        return new MCBlockPos(blockPos.offset(EnumFacing.valueOf(directionName), offset));
    }

    @Override
    public Object getInternal() {
        return blockPos;
    }
}
