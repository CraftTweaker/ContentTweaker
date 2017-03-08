package com.teamacronymcoders.contenttweaker.modules.vanilla.blocks;

import com.teamacronymcoders.base.blocks.BlockBase;
import com.teamacronymcoders.contenttweaker.api.MissingFieldsException;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class BlockContent extends BlockBase {
    private BlockRepresentation blockRepresentation;

    private CreativeTabs creativeTab = CreativeTabs.MISC;

    public BlockContent(BlockRepresentation blockRepresentation) {
        super(blockRepresentation.getBlockMaterial());
        checkFields(blockRepresentation);
        setFields(blockRepresentation);
    }

    /* Beginning of Representation stuff */
    public void checkFields(BlockRepresentation blockRepresentation) {
        List<String> missingFields = new ArrayList<>();
        if (blockRepresentation.getUnlocalizedName() == null) {
            missingFields.add("unlocalizedName");
        }
        if (!missingFields.isEmpty()) {
            throw new MissingFieldsException("Block", missingFields);
        }
    }

    public void setFields(BlockRepresentation blockRepresentation) {
        this.setUnlocalizedName(blockRepresentation.getUnlocalizedName());
        this.setCreativeTab(blockRepresentation.getCreativeTab());
        this.setLightOpacity(blockRepresentation.getLightOpacity());
        this.setLightLevel(blockRepresentation.getLightValue());
        this.setHardness(blockRepresentation.getBlockHardness());
        this.setResistance(blockRepresentation.getBlockResistance());
        this.setHarvestLevel(blockRepresentation.getToolClass(), blockRepresentation.getToolLevel());
        this.setSoundType(blockRepresentation.getBlockSoundType());
    }

    @Nonnull
    @Override
    @SideOnly(Side.CLIENT)
    public CreativeTabs getCreativeTabToDisplayOn() {
        return this.getCreativeTab();
    }

    public CreativeTabs getCreativeTab() {
        return this.creativeTab;
    }

    @Override
    @Nonnull
    public Block setCreativeTab(@Nonnull CreativeTabs creativeTab) {
        this.creativeTab = creativeTab;
        return this;
    }
}
