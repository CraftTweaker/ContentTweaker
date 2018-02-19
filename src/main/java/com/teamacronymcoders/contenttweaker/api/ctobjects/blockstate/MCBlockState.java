package com.teamacronymcoders.contenttweaker.api.ctobjects.blockstate;

import com.teamacronymcoders.contenttweaker.api.ctobjects.blockpos.IBlockPos;
import com.teamacronymcoders.contenttweaker.api.ctobjects.enums.Facing;
import com.teamacronymcoders.contenttweaker.api.ctobjects.enums.PushReaction;
import com.teamacronymcoders.contenttweaker.api.ctobjects.world.IWorld;
import crafttweaker.api.block.IBlock;
import crafttweaker.mc1120.block.MCSpecificBlock;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;

public class MCBlockState implements ICTBlockState {
    private IBlockState blockState;
    private IBlock block;

    @SuppressWarnings("deprecation")
    public MCBlockState(IBlock block) {
        this.block = block;
        Block actualBlock = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(block.getDefinition().getId()));
        if (actualBlock != null) {
            this.blockState = actualBlock.getStateFromMeta(block.getMeta());
        }
    }

    public MCBlockState(IBlockState blockState) {
        this.blockState = blockState;
        Block block = blockState.getBlock();
        int meta = block.getMetaFromState(blockState);
        this.block = new MCSpecificBlock(block, meta);
    }

    @Override
    public IBlock getBlock() {
        return this.block;
    }

    @Override
    public int getMeta() {
        return this.getBlock().getMeta();
    }

    @Override
    public boolean isReplaceable(IWorld world, IBlockPos blockPos) {
        return this.blockState.getBlock().isReplaceable(world.getInternal(), blockPos.getInternal());
    }

    @Override
    public int getLightValue(IWorld world, IBlockPos blockPos) {
        return this.blockState.getLightValue(world.getInternal(), blockPos.getInternal());
    }

    @Override
    public boolean canProvidePower() {
        return this.blockState.canProvidePower();
    }

    @Override
    public int getWeakPower(IWorld world, IBlockPos blockPos, Facing facing) {
        return this.blockState.getWeakPower(world.getInternal(), blockPos.getInternal(), facing.getInternal());
    }

    @Override
    public int getComparatorInputOverride(IWorld world, IBlockPos blockPos) {
        return this.blockState.getComparatorInputOverride(world.getInternal(), blockPos.getInternal());
    }

    @Override
    public PushReaction getMobilityFlag() {
        return PushReaction.of(this.blockState.getMobilityFlag());
    }

    @Override
    public int compare(ICTBlockState other) {
        int result = 0;
        if (!this.getInternal().equals(other.getInternal())) {
            if (this.getInternal().getBlock().equals(other.getInternal().getBlock())) {
                result = Integer.compare(this.getMeta(), other.getMeta());
            } else {
                int blockId = ((ForgeRegistry<Block>)ForgeRegistries.BLOCKS).getID(this.getInternal().getBlock());
                int otherBlockId = ((ForgeRegistry<Block>)ForgeRegistries.BLOCKS).getID(other.getInternal().getBlock());
                result = Integer.compare(blockId, otherBlockId);
            }
        }
        return result;
    }

    @Override
    public IBlockState getInternal() {
        return this.blockState;
    }
}
