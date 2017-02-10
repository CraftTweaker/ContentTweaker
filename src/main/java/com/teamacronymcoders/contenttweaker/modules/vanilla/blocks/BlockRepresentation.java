package com.teamacronymcoders.contenttweaker.modules.vanilla.blocks;

import com.teamacronymcoders.base.registry.BlockRegistry;
import com.teamacronymcoders.contenttweaker.ContentTweaker;
import com.teamacronymcoders.contenttweaker.api.IRepresentation;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("contenttweaker.content.Block")
public class BlockRepresentation implements IRepresentation {
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

    @ZenMethod
    public String getUnlocalizedName() {
        return unlocalizedName;
    }

    @ZenMethod
    public void setUnlocalizedName(String unlocalizedName) {
        this.unlocalizedName = unlocalizedName;
    }

    @ZenMethod
    public CreativeTabs getCreativeTabs() {
        return creativeTabs;
    }

    @ZenMethod
    public void setCreativeTabs(CreativeTabs creativeTabs) {
        this.creativeTabs = creativeTabs;
    }

    @ZenMethod
    public boolean isFullBlock() {
        return fullBlock;
    }

    @ZenMethod
    public void setFullBlock(boolean fullBlock) {
        this.fullBlock = fullBlock;
    }

    @ZenMethod
    public int getLightOpacity() {
        return lightOpacity;
    }

    @ZenMethod
    public void setLightOpacity(int lightOpacity) {
        this.lightOpacity = lightOpacity;
    }

    @ZenMethod
    public boolean isTranslucent() {
        return translucent;
    }

    @ZenMethod
    public void setTranslucent(boolean translucent) {
        this.translucent = translucent;
    }

    @ZenMethod
    public int getLightValue() {
        return lightValue;
    }

    @ZenMethod
    public void setLightValue(int lightValue) {
        this.lightValue = lightValue;
    }

    @ZenMethod
    public float getBlockHardness() {
        return blockHardness;
    }

    @ZenMethod
    public void setBlockHardness(float blockHardness) {
        this.blockHardness = blockHardness;
    }

    @ZenMethod
    public float getBlockResistance() {
        return blockResistance;
    }

    @ZenMethod
    public void setBlockResistance(float blockResistance) {
        this.blockResistance = blockResistance;
    }

    @ZenMethod
    public String getToolClass() {
        return toolClass;
    }

    @ZenMethod
    public void setToolClass(String toolClass) {
        this.toolClass = toolClass;
    }

    @ZenMethod
    public int getToolLevel() {
        return toolLevel;
    }

    @ZenMethod
    public void setToolLevel(int toolLevel) {
        this.toolLevel = toolLevel;
    }

    @ZenMethod
    public SoundType getBlockSoundType() {
        return blockSoundType;
    }

    @ZenMethod
    public void setBlockSoundType(SoundType blockSoundType) {
        this.blockSoundType = blockSoundType;
    }

    @ZenMethod
    public Material getBlockMaterial() {
        return blockMaterial;
    }

    @ZenMethod
    public void setBlockMaterial(Material blockMaterial) {
        this.blockMaterial = blockMaterial;
    }

    @Override
    public String getName() {
        return this.getUnlocalizedName();
    }

    @Override
    public String getTypeName() {
        return "Block";
    }

    @Override
    public void register() {
        ContentTweaker.instance.getRegistry(BlockRegistry.class, "BLOCK").register(new BlockContent(this));
    }
}
