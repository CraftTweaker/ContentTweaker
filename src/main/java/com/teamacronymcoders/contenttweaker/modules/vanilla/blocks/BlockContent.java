package com.teamacronymcoders.contenttweaker.modules.vanilla.blocks;

import net.minecraft.block.Block;

public class BlockContent extends Block {
    private BlockRepresentation blockRepresentation;
    public BlockContent(BlockRepresentation blockRepresentation) {
        super(blockRepresentation.getBlockMaterial());
        setFields(blockRepresentation);
    }

    /* Beginning of Representation stuff */
    public void setFields(BlockRepresentation blockRepresentation) {
        this.setUnlocalizedName(blockRepresentation.getUnlocalizedName());
        this.setCreativeTab(blockRepresentation.getCreativeTabs());
        this.useNeighborBrightness = blockRepresentation.getUseNeighborBrightness();
    }
}
