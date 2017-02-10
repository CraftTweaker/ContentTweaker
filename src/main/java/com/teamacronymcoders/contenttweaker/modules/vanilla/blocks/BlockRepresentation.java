package com.teamacronymcoders.contenttweaker.modules.vanilla.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockRepresentation {
    private String unlocalizedName;
    private CreativeTabs creativeTabs = CreativeTabs.MISC;
    private boolean fullBlock = true;
    private int lightOpacity = 0;
    private boolean translucent = false;
    private int lightValue = 0;
    private float blockHardness = 5;
    private float blockResistance = 5;
    private String toolClass = "pickaxe";
    private int toolLevel = 2;
    private SoundType blockSoundType = SoundType.METAL;
    private Material blockMaterial = Material.IRON;

    public String getUnlocalizedName() {
        return unlocalizedName;
    }

    public void setUnlocalizedName(String unlocalizedName) {
        this.unlocalizedName = unlocalizedName;
    }

    public CreativeTabs getCreativeTabs() {
        return creativeTabs;
    }

    public void setCreativeTabs(CreativeTabs creativeTabs) {
        this.creativeTabs = creativeTabs;
    }

    public boolean isFullBlock() {
        return fullBlock;
    }

    public void setFullBlock(boolean fullBlock) {
        this.fullBlock = fullBlock;
    }

    public int getLightOpacity() {
        return lightOpacity;
    }

    public void setLightOpacity(int lightOpacity) {
        this.lightOpacity = lightOpacity;
    }

    public boolean isTranslucent() {
        return translucent;
    }

    public void setTranslucent(boolean translucent) {
        this.translucent = translucent;
    }

    public int getLightValue() {
        return lightValue;
    }

    public void setLightValue(int lightValue) {
        this.lightValue = lightValue;
    }

    public float getBlockHardness() {
        return blockHardness;
    }

    public void setBlockHardness(float blockHardness) {
        this.blockHardness = blockHardness;
    }

    public float getBlockResistance() {
        return blockResistance;
    }

    public void setBlockResistance(float blockResistance) {
        this.blockResistance = blockResistance;
    }

    public String getToolClass() {
        return toolClass;
    }

    public void setToolClass(String toolClass) {
        this.toolClass = toolClass;
    }

    public int getToolLevel() {
        return toolLevel;
    }

    public void setToolLevel(int toolLevel) {
        this.toolLevel = toolLevel;
    }

    public SoundType getBlockSoundType() {
        return blockSoundType;
    }

    public void setBlockSoundType(SoundType blockSoundType) {
        this.blockSoundType = blockSoundType;
    }

    public Material getBlockMaterial() {
        return blockMaterial;
    }

    public void setBlockMaterial(Material blockMaterial) {
        this.blockMaterial = blockMaterial;
    }
}
