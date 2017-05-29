package com.teamacronymcoders.contenttweaker.api.wrappers.world;

import com.teamacronymcoders.contenttweaker.api.wrappers.blockpos.IBlockPos;
import com.teamacronymcoders.contenttweaker.api.wrappers.blockstate.ICTBlockState;
import com.teamacronymcoders.contenttweaker.api.wrappers.blockstate.MCBlockState;
import minetweaker.api.block.IBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MCWorld implements IWorld {
    private World world;

    public MCWorld(World world) {
        this.world = world;
    }

    @Override
    public boolean isRemote() {
        return world.isRemote;
    }

    @Override
    public boolean isRaining() {
        return world.getWorldInfo().isRaining();
    }

    @Override
    public boolean isThundering() {
        return world.getWorldInfo().isThundering();
    }

    @Override
    public boolean setBlockState(IBlock blockState, IBlockPos blockPos) {
        ICTBlockState ictBlockState = new MCBlockState(blockState);
        boolean setBlock = false;
        if (ictBlockState.getInternal() instanceof IBlockState && blockPos.getInternal() instanceof BlockPos) {
            setBlock = this.world.setBlockState((BlockPos) blockPos.getInternal(), (IBlockState)ictBlockState.getInternal(), 2);
        }
        return setBlock;
    }

    @Override
    public Object getInternal() {
        return world;
    }
}
