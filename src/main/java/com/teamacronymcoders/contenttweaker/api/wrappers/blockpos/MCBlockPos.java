package com.teamacronymcoders.contenttweaker.api.wrappers.blockpos;

import net.minecraft.util.math.BlockPos;

public class MCBlockPos implements IBlockPos {
    private BlockPos blockPos;

    public MCBlockPos(BlockPos blockPos) {
        this.blockPos = blockPos;
    }
}
