package com.teamacronymcoders.contenttweaker.api.wrappers.blockstate;

import com.teamacronymcoders.base.registrysystem.BlockRegistry;
import minetweaker.api.block.IBlock;
import minetweaker.mc1112.block.MCSpecificBlock;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class MCBlockState implements ICTBlockState {
    private IBlockState blockState;
    private IBlock block;

    @SuppressWarnings("deprecation")
    public MCBlockState(IBlock block) {
        this.block = block;
        Block actualBlock = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(block.getDefinition().getId()));
        this.blockState = actualBlock.getStateFromMeta(block.getMeta());
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
    public Object getInternal() {
        return this.blockState;
    }
}
