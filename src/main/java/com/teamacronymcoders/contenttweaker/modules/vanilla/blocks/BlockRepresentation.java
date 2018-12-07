package com.teamacronymcoders.contenttweaker.modules.vanilla.blocks;

import com.teamacronymcoders.base.registrysystem.BlockRegistry;
import com.teamacronymcoders.contenttweaker.ContentTweaker;
import com.teamacronymcoders.contenttweaker.api.IRepresentation;
import com.teamacronymcoders.contenttweaker.api.ctobjects.aabb.MCAxisAlignedBB;
import com.teamacronymcoders.contenttweaker.api.ctobjects.blockmaterial.BlockMaterialDefinition;
import com.teamacronymcoders.contenttweaker.api.ctobjects.blockmaterial.IBlockMaterialDefinition;
import com.teamacronymcoders.contenttweaker.api.ctobjects.enums.PushReaction;
import com.teamacronymcoders.contenttweaker.modules.vanilla.functions.IBlockAction;
import com.teamacronymcoders.contenttweaker.modules.vanilla.functions.IBlockDropHandler;
import com.teamacronymcoders.contenttweaker.modules.vanilla.items.ICreativeTab;
import com.teamacronymcoders.contenttweaker.modules.vanilla.resources.creativetab.MCCreativeTab;
import com.teamacronymcoders.contenttweaker.modules.vanilla.resources.sounds.ISoundTypeDefinition;
import com.teamacronymcoders.contenttweaker.modules.vanilla.resources.sounds.SoundTypeDefinition;
import com.teamacronymcoders.contenttweaker.modules.vanilla.tileentity.TileEntityRepresentation;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.ResourceLocation;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenProperty;

@ZenClass("mods.contenttweaker.Block")
public class BlockRepresentation implements IRepresentation<Block> {
    @ZenProperty
    public String unlocalizedName;
    @ZenProperty
    public ICreativeTab creativeTab = new MCCreativeTab(CreativeTabs.MISC);
    @ZenProperty
    public boolean fullBlock = true;
    @ZenProperty
    public boolean gravity = false;
    @ZenProperty
    public int lightOpacity = 255;
    @ZenProperty
    public boolean translucent = false;
    @ZenProperty
    public float lightValue = 0;
    @ZenProperty
    public float blockHardness = 5;
    @ZenProperty
    public float blockResistance = 5;
    @ZenProperty
    public String toolClass = "pickaxe";
    @ZenProperty
    public int toolLevel = 2;
    @ZenProperty
    public ISoundTypeDefinition blockSoundType = new SoundTypeDefinition(SoundType.METAL);
    @ZenProperty
    public IBlockMaterialDefinition blockMaterial = new BlockMaterialDefinition(Material.IRON);
    @ZenProperty
    public float enchantPowerBonus = 0;
    @ZenProperty
    public String enumBlockRenderType = EnumBlockRenderType.MODEL.toString();
    @ZenProperty
    public float slipperiness = 0.6f;
    @ZenProperty
    public IBlockAction onBlockPlace;
    @ZenProperty
    public IBlockAction onBlockBreak;
    @ZenProperty
    public String blockLayer = BlockRenderLayer.SOLID.toString();
    @ZenProperty
    public MCAxisAlignedBB axisAlignedBB = MCAxisAlignedBB.create(0, 0, 0, 1, 1, 1);
    @ZenProperty
    public IBlockAction onUpdateTick;
    @ZenProperty
    public IBlockAction onRandomTick;
    @ZenProperty
    public PushReaction mobilityFlag;
    @ZenProperty
    public boolean passable = !this.blockMaterial.blocksMovement();
    @ZenProperty
    public boolean entitySpawnable = true;
    @ZenProperty
    public IBlockDropHandler dropHandler;
    @ZenProperty
    public boolean beaconBase = false;
    @ZenProperty
    public TileEntityRepresentation tileEntityRepresentation = null;

    @ZenMethod
    public String getUnlocalizedName() {
        return unlocalizedName;
    }

    @ZenMethod
    public void setUnlocalizedName(String unlocalizedName) {
        this.unlocalizedName = unlocalizedName;
    }

    @ZenMethod
    public void setCreativeTab(ICreativeTab creativeTab) {
        this.creativeTab = creativeTab;
    }

    @ZenMethod
    public ICreativeTab getCreativeTab() {
        return creativeTab;
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
    public boolean hasGravity() {
        return gravity;
    }

    @ZenMethod
    public void setHasGravity(boolean gravity) {
        this.gravity = gravity;
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
    public float getLightValue() {
        return lightValue;
    }

    @ZenMethod
    public void setLightValue(float lightValue) {
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
    public ISoundTypeDefinition getBlockSoundType() {
        return this.blockSoundType;
    }

    @ZenMethod
    public void setBlockSoundType(ISoundTypeDefinition blockSoundType) {
        this.blockSoundType = blockSoundType;
    }

    @ZenMethod
    public IBlockMaterialDefinition getBlockMaterial() {
        return this.blockMaterial;
    }

    @ZenMethod
    public void setBlockMaterial(IBlockMaterialDefinition material) {
        this.blockMaterial = material;
    }

    @ZenMethod
    public void setEnchantPowerBonus(float enchantPowerBonus) {
        this.enchantPowerBonus = enchantPowerBonus;
    }

    @ZenMethod
    public float getEnchantPowerBonus() {
        return enchantPowerBonus;
    }

    @ZenMethod
    public void setEnumBlockRenderType(String blockRenderType) {
        this.enumBlockRenderType = blockRenderType;
    }

    @ZenMethod
    public String getEnumBlockRenderType() {
        return this.enumBlockRenderType;
    }

    @ZenMethod
    public void setSlipperiness(float slipperiness) {
        this.slipperiness = slipperiness;
    }

    @ZenMethod
    public float getSlipperiness() {
        return this.slipperiness;
    }

    @ZenMethod
    public void setOnBlockBreak(IBlockAction iBlockAction) {
        this.onBlockBreak = iBlockAction;
    }

    @ZenMethod
    public IBlockAction getOnBlockBreak() {
        return this.onBlockBreak;
    }

    @ZenMethod
    public void setOnBlockPlace(IBlockAction iBlockAction) {
        this.onBlockPlace = iBlockAction;
    }

    @ZenMethod
    public IBlockAction getOnBlockPlace() {
        return this.onBlockPlace;
    }

    @ZenMethod
    public void setBlockLayer(String blockLayer) {
        this.blockLayer = blockLayer;
    }

    @ZenMethod
    public String getBlockLayer() {
        return this.blockLayer;
    }

    @ZenMethod
    public MCAxisAlignedBB getAxisAlignedBB() {
        return axisAlignedBB;
    }

    @ZenMethod
    public void setAxisAlignedBB(MCAxisAlignedBB axisAlignedBB) {
        this.axisAlignedBB = axisAlignedBB;
    }

    @ZenMethod
    public IBlockAction getOnUpdateTick() {
        return onUpdateTick;
    }

    @ZenMethod
    public void setOnUpdateTick(IBlockAction onUpdateTick) {
        this.onUpdateTick = onUpdateTick;
    }

    @ZenMethod
    public IBlockAction getOnRandomTick() {
        return onRandomTick;
    }

    @ZenMethod
    public void setOnRandomTick(IBlockAction onRandomTick) {
        this.onRandomTick = onRandomTick;
    }

    @ZenMethod
    public PushReaction getMobilityFlag() {
        return mobilityFlag;
    }

    @ZenMethod
    public void setMobilityFlag(PushReaction mobilityFlag) {
        this.mobilityFlag = mobilityFlag;
    }

    @ZenMethod
    public boolean isPassable() {
        return passable;
    }

    @ZenMethod
    public void setPassable(boolean passable) {
        this.passable = passable;
    }

    @ZenMethod
    public void setEntitySpawnable(boolean entitySpawnable) {
        this.entitySpawnable = entitySpawnable;
    }

    @ZenMethod
    public boolean canEntitySpawn() {
        return entitySpawnable;
    }

    @ZenMethod
    public IBlockDropHandler getDropHandler() {
        return dropHandler;
    }

    @ZenMethod
    public void setDropHandler(IBlockDropHandler dropHandler) {
        this.dropHandler = dropHandler;
    }

    @Override
    public Block getInternal() {
        return ContentTweaker.instance.getRegistry(BlockRegistry.class, "BLOCK").get(new ResourceLocation(
                ContentTweaker.MOD_ID, this.getUnlocalizedName()));
    }

    @Override
    public String getName() {
        return this.getUnlocalizedName();
    }

    @Override
    public String getTypeName() {
        return "BlockRepresentation";
    }

    @ZenMethod
    public void register() {
        ContentTweaker.instance.getRegistry(BlockRegistry.class, "BLOCK").register(new BlockContent(this));
    }
}
