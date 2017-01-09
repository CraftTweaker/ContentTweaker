package com.teamacronymcoders.contenttweaker.modules.vanilla.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockContent extends Block {
    private BlockRepresentation blockRepresentation;
    public BlockContent(BlockRepresentation blockRepresentation) {
        super(blockRepresentation.getBlockMaterial() != null ? blockRepresentation.getBlockMaterial() : Material.IRON);
        checkFields(blockRepresentation);
        setFields(blockRepresentation);
    }

    /* Beginning of Representation stuff */
    public void checkFields(BlockRepresentation blockRepresentation) {

    }

    public void setFields(BlockRepresentation blockRepresentation) {
        this.setUnlocalizedName(blockRepresentation.getUnlocalizedName());
        this.setCreativeTab(blockRepresentation.getCreativeTabs());
        this.useNeighborBrightness = blockRepresentation.getUseNeighborBrightness();
    }
}
