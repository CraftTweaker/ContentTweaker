package com.teamacronymcoders.contenttweaker.modules.vanilla.blocks;

import com.teamacronymcoders.base.registry.BlockRegistry;
import com.teamacronymcoders.contenttweaker.ContentTweaker;
import com.teamacronymcoders.contenttweaker.api.IRepresentation;
import com.teamacronymcoders.contenttweaker.modules.vanilla.functions.IBlockAction;
import com.teamacronymcoders.contenttweaker.modules.vanilla.items.ICreativeTab;
import com.teamacronymcoders.contenttweaker.modules.vanilla.resources.creativetab.MCCreativeTab;
import com.teamacronymcoders.contenttweaker.modules.vanilla.resources.materials.IMaterialDefinition;
import com.teamacronymcoders.contenttweaker.modules.vanilla.resources.sounds.ISoundTypeDefinition;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;

import java.util.Locale;

public class BlockRepresentation implements IRepresentation, IBlock {
    private String unlocalizedName;
    private CreativeTabs creativeTab = CreativeTabs.MISC;
    private boolean fullBlock = true;
    private int lightOpacity = 255;
    private boolean translucent = false;
    private float lightValue = 0;
    private float blockHardness = 5;
    private float blockResistance = 5;
    private String toolClass = "pickaxe";
    private int toolLevel = 2;
    private SoundType blockSoundType = SoundType.METAL;
    private Material blockMaterial = Material.IRON;
    private float enchantPowerBonus = 0;
    private EnumBlockRenderType enumBlockRenderType = EnumBlockRenderType.MODEL;
    private float slipperiness = 0.6f;
    private IBlockAction onBlockPlace;
    private IBlockAction onBlockBreak;
    private BlockRenderLayer blockLayer = BlockRenderLayer.SOLID;

    private BlockContent blockContent;


    @Override
    public String getUnlocalizedName() {
        return unlocalizedName;
    }

    @Override
    public void setUnlocalizedName(String unlocalizedName) {
        this.unlocalizedName = unlocalizedName;
    }

    public CreativeTabs getInternalCreativeTab() {
        return creativeTab;
    }

    @Override
    public void setCreativeTab(ICreativeTab creativeTab) {
        if (creativeTab.getInternal() instanceof CreativeTabs) {
            this.creativeTab = (CreativeTabs) creativeTab.getInternal();
        }
    }

    @Override
    public ICreativeTab getCreativeTab() {
        return new MCCreativeTab(creativeTab);
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
    public float getLightValue() {
        return lightValue;
    }

    @Override
    public void setLightValue(float lightValue) {
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

    @Override
    public ISoundTypeDefinition getBlockSoundType() {
        return null;
    }

    public SoundType getInternalBlockSoundType() {
        return blockSoundType;
    }

    public void setBlockSoundType(SoundType blockSoundType) {
        this.blockSoundType = blockSoundType;
    }

    @Override
    public void setBlockSoundType(ISoundTypeDefinition blockSoundType) {
        this.setBlockSoundType((SoundType) blockSoundType.getInternal());
    }

    @Override
    public IMaterialDefinition getBlockMaterial() {
        return null;
    }

    public Material getInternalBlockMaterial() {
        return blockMaterial;
    }

    public void setBlockMaterial(Material blockMaterial) {
        this.blockMaterial = blockMaterial;
    }

    @Override
    public void setBlockMaterial(IMaterialDefinition material) {
        this.setBlockMaterial((Material) material.getInternal());
    }

    @Override
    public void setEnchantPowerBonus(float enchantPowerBonus) {
        this.enchantPowerBonus = enchantPowerBonus;
    }

    @Override
    public float getEnchantPowerBonus() {
        return enchantPowerBonus;
    }

    @Override
    public void setEnumBlockRenderType(String blockRenderType) {
        this.enumBlockRenderType = EnumBlockRenderType.valueOf(blockRenderType.toUpperCase(Locale.US));
    }

    @Override
    public String getEnumBlockRenderType() {
        return this.enumBlockRenderType.name();
    }

    @Override
    public void setSlipperiness(float slipperiness) {
        this.slipperiness = slipperiness;
    }

    @Override
    public float getSlipperiness() {
        return this.slipperiness;
    }

    @Override
    public void setOnBlockBreak(IBlockAction iBlockAction) {
        this.onBlockBreak = iBlockAction;
    }

    @Override
    public IBlockAction getOnBlockBreak() {
        return this.onBlockBreak;
    }

    @Override
    public void setOnBlockPlace(IBlockAction iBlockAction) {
        this.onBlockPlace = iBlockAction;
    }

    @Override
    public IBlockAction getOnBlockAdded() {
        return this.onBlockPlace;
    }

    @Override
    public void setBlockLayer(String blockLayer) {
        this.blockLayer = BlockRenderLayer.valueOf(blockLayer.toUpperCase(Locale.US));
    }

    @Override
    public String getBlockLayer() {
        return this.blockLayer.toString();
    }

    public BlockRenderLayer getInternalBlockLayer() {
        return this.blockLayer;
    }

    public EnumBlockRenderType getInternalBlockRenderType() {
        return this.enumBlockRenderType;
    }

    @Override
    public Object getInternal() {
        return this.blockContent;
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
        this.blockContent = new BlockContent(this);
        ContentTweaker.instance.getRegistry(BlockRegistry.class, "BLOCK").register(this.blockContent);
    }
}
