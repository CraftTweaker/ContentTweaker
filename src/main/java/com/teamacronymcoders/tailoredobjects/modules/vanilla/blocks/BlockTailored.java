package com.teamacronymcoders.tailoredobjects.modules.vanilla.blocks;

import com.teamacronymcoders.tailoredobjects.representations.blocks.BlockRepresentation;
import net.minecraft.block.Block;

public class BlockTailored extends Block {
    private BlockRepresentation blockRepresentation;
    public BlockTailored(BlockRepresentation blockRepresentation) {
        super(blockRepresentation.getMaterial());
        setFields(blockRepresentation);
    }

    protected void setFields(BlockRepresentation blockRepresentation) {
        this.slipperiness = blockRepresentation.getSlipperiness();

    }
}
