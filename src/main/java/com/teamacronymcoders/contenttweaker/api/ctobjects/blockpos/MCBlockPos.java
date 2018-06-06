package com.teamacronymcoders.contenttweaker.api.ctobjects.blockpos;

import com.teamacronymcoders.contenttweaker.api.ctobjects.enums.Facing;
import crafttweaker.api.util.Position3f;
import crafttweaker.api.world.IFacing;
import crafttweaker.mc1120.util.MCPosition3f;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.util.Locale;

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
    public crafttweaker.api.world.IBlockPos getOffset(IFacing direction, int offset) {
        return getOffset(direction.getName(), offset);
    }

    @Override
    public Position3f asPosition3f() {
        return new MCPosition3f(getX(), getY(), getZ());
    }

    @Override
    public IBlockPos getOffset(String directionName, int offset) {
        return new MCBlockPos(blockPos.offset(EnumFacing.valueOf(directionName.toUpperCase(Locale.US)), offset));
    }

    @Override
    public IBlockPos getOffset(Facing facing, int offset) {
        return new MCBlockPos(blockPos.offset(facing.getInternal(), offset));
    }

    @Override
    public BlockPos getInternal() {
        return blockPos;
    }
}
