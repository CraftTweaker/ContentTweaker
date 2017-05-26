package com.teamacronymcoders.contenttweaker.api.wrappers.blockstate;

import net.minecraft.block.state.IBlockState;

public class MCBlockState implements ICTBlockState {
    private IBlockState blockState;

    public MCBlockState(IBlockState blockState) {
        this.blockState = blockState;
    }
}
