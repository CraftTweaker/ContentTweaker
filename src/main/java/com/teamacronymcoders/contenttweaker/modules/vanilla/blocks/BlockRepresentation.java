package com.teamacronymcoders.contenttweaker.modules.vanilla.blocks;

import com.teamacronymcoders.base.registry.BlockRegistry;
import com.teamacronymcoders.contenttweaker.ContentTweaker;
import com.teamacronymcoders.contenttweaker.api.ContentTweakerAPI;
import com.teamacronymcoders.contenttweaker.api.IRepresentation;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockRepresentation implements IRepresentation, IBlock {
    private String unlocalizedName;
    private CreativeTabs creativeTab = CreativeTabs.MISC;
    private boolean fullBlock = true;
    private int lightOpacity = 255;
    private boolean translucent = false;
    private int lightValue = 0;
    private float blockHardness = 5;
    private float blockResistance = 5;
    private String toolClass = "pickaxe";
    private int toolLevel = 2;
    private SoundType blockSoundType = SoundType.METAL;
    private Material blockMaterial = Material.IRON;

    @Override
    public String getUnlocalizedName() {
        return unlocalizedName;
    }

    @Override
    public void setUnlocalizedName(String unlocalizedName) {
        this.unlocalizedName = unlocalizedName;
    }

    public CreativeTabs getCreativeTab() {
        return creativeTab;
    }

    public void setCreativeTab(CreativeTabs creativeTab) {
        this.creativeTab = creativeTab;
    }

    @Override
    public void setCreativeTab(String creativeTabName) {
        this.creativeTab = ContentTweakerAPI.getInstance().getCreativeTabs().getResource(creativeTabName);
    }

    @Override
    public boolean isFullBlock() {
        return fullBlock;
    }

    @Override
    public void setFullBlock(boolean fullBlock) {
        this.fullBlock = fullBlock;
    }

    @Override
    public int getLightOpacity() {
        return lightOpacity;
    }

    @Override
    public void setLightOpacity(int lightOpacity) {
        this.lightOpacity = lightOpacity;
    }

    @Override
    public boolean isTranslucent() {
        return translucent;
    }

    @Override
    public void setTranslucent(boolean translucent) {
        this.translucent = translucent;
    }

    @Override
    public int getLightValue() {
        return lightValue;
    }

    @Override
    public void setLightValue(int lightValue) {
        this.lightValue = lightValue;
    }

    @Override
    public float getBlockHardness() {
        return blockHardness;
    }

    @Override
    public void setBlockHardness(float blockHardness) {
        this.blockHardness = blockHardness;
    }

    @Override
    public float getBlockResistance() {
        return blockResistance;
    }

    @Override
    public void setBlockResistance(float blockResistance) {
        this.blockResistance = blockResistance;
    }

    @Override
    public String getToolClass() {
        return toolClass;
    }

    @Override
    public void setToolClass(String toolClass) {
        this.toolClass = toolClass;
    }

    @Override
    public int getToolLevel() {
        return toolLevel;
    }

    @Override
    public void setToolLevel(int toolLevel) {
        this.toolLevel = toolLevel;
    }

    public SoundType getBlockSoundType() {
        return blockSoundType;
    }

    public void setBlockSoundType(SoundType blockSoundType) {
        this.blockSoundType = blockSoundType;
    }

    @Override
    public void setBlockSoundType(String blockSoundTypeName) {
        this.blockSoundType = ContentTweakerAPI.getInstance().getSoundTypes().getResource(blockSoundTypeName);
    }

    public Material getBlockMaterial() {
        return blockMaterial;
    }

    public void setBlockMaterial(Material blockMaterial) {
        this.blockMaterial = blockMaterial;
    }

    @Override
    public void setBlockMaterial(String blockMaterialName) {
        this.blockMaterial = ContentTweakerAPI.getInstance().getBlockMaterials().getResource(blockMaterialName);
    }

    @Override
    public Object getInternal() {
        return this;
    }

    @Override
    public String getName() {
        return this.getUnlocalizedName();
    }

    @Override
    public String getTypeName() {
        return "BlockRepresentation";
    }

    @Override
    public void register() {
        ContentTweaker.instance.getRegistry(BlockRegistry.class, "BLOCK").register(new BlockContent(this));
    }
}
