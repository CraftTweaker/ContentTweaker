package com.teamacronymcoders.contenttweaker.modules.vanilla.blocks;

import com.teamacronymcoders.base.registrysystem.BlockRegistry;
import com.teamacronymcoders.contenttweaker.ContentTweaker;
import com.teamacronymcoders.contenttweaker.api.IRepresentation;
import com.teamacronymcoders.contenttweaker.api.ctobjects.aabb.MCAxisAlignedBB;
import com.teamacronymcoders.contenttweaker.api.ctobjects.blockmaterial.BlockMaterialDefinition;
import com.teamacronymcoders.contenttweaker.api.ctobjects.blockmaterial.IBlockMaterialDefinition;
import com.teamacronymcoders.contenttweaker.api.ctobjects.color.CTColor;
import com.teamacronymcoders.contenttweaker.api.ctobjects.enums.FaceShape;
import com.teamacronymcoders.contenttweaker.api.ctobjects.enums.PushReaction;
import com.teamacronymcoders.contenttweaker.api.ctobjects.resourcelocation.CTResourceLocation;
import com.teamacronymcoders.contenttweaker.modules.vanilla.functions.IBlockAction;
import com.teamacronymcoders.contenttweaker.modules.vanilla.functions.IBlockColorSupplier;
import com.teamacronymcoders.contenttweaker.modules.vanilla.functions.IBlockDropHandler;
import com.teamacronymcoders.contenttweaker.modules.vanilla.functions.IBlockFaceSupplier;
import com.teamacronymcoders.contenttweaker.modules.vanilla.functions.IItemColorSupplier;
import com.teamacronymcoders.contenttweaker.modules.vanilla.items.ICreativeTab;
import com.teamacronymcoders.contenttweaker.modules.vanilla.resources.creativetab.MCCreativeTab;
import com.teamacronymcoders.contenttweaker.modules.vanilla.resources.sounds.ISoundTypeDefinition;
import com.teamacronymcoders.contenttweaker.modules.vanilla.resources.sounds.SoundTypeDefinition;
import com.teamacronymcoders.contenttweaker.modules.vanilla.tileentity.TileEntityRepresentation;
import crafttweaker.annotations.ZenRegister;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.ResourceLocation;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenProperty;

@ZenRegister
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
    public PushReaction mobilityFlag = PushReaction.normal();
    @ZenProperty
    public boolean passable = !this.blockMaterial.blocksMovement();
    @ZenProperty
    public boolean replaceable = this.blockMaterial.isReplaceable();
    @ZenProperty
    public boolean entitySpawnable = true;
    @ZenProperty
    public boolean witherProof = false;
    @ZenProperty
    public IBlockDropHandler dropHandler;
    @ZenProperty
    public boolean beaconBase = false;
    @ZenProperty
    public TileEntityRepresentation tileEntityRepresentation = null;
    @ZenProperty
    public IBlockColorSupplier blockColorSupplier = (state, access, pos, tint) -> CTColor.fromInt(-1);
    @ZenProperty
    public IItemColorSupplier itemColorSupplier = (itemStack, tint) -> CTColor.fromInt(-1);
    @ZenProperty
    public IBlockFaceSupplier blockFaceSupplier = (access, state, pos, face) -> FaceShape.solid();
    @ZenProperty
    public CTResourceLocation textureLocation;
    @ZenProperty
    public boolean canSilkHarvest = true;

    @ZenMethod
    public String getUnlocalizedName() {
        return unlocalizedName;
    }

    @ZenMethod
    public BlockRepresentation setUnlocalizedName(String unlocalizedName) {
        this.unlocalizedName = unlocalizedName;
        return this;
    }

    @ZenMethod
    public BlockRepresentation setCreativeTab(ICreativeTab creativeTab) {
        this.creativeTab = creativeTab;
        return this;
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
    public BlockRepresentation setFullBlock(boolean fullBlock) {
        this.fullBlock = fullBlock;
        return this;
    }

    @ZenMethod
    public boolean hasGravity() {
        return gravity;
    }

    @ZenMethod
    public BlockRepresentation setHasGravity(boolean gravity) {
        this.gravity = gravity;
        return this;
    }

    @ZenMethod
    public int getLightOpacity() {
        return lightOpacity;
    }

    @ZenMethod
    public BlockRepresentation setLightOpacity(int lightOpacity) {
        this.lightOpacity = lightOpacity;
        return this;
    }

    @ZenMethod
    public boolean isTranslucent() {
        return translucent;
    }

    @ZenMethod
    public BlockRepresentation setTranslucent(boolean translucent) {
        this.translucent = translucent;
        return this;
    }

    @ZenMethod
    public float getLightValue() {
        return lightValue;
    }

    @ZenMethod
    public BlockRepresentation setLightValue(float lightValue) {
        this.lightValue = lightValue;
        return this;
    }

    @ZenMethod
    public float getBlockHardness() {
        return blockHardness;
    }

    @ZenMethod
    public BlockRepresentation setBlockHardness(float blockHardness) {
        this.blockHardness = blockHardness;
        return this;
    }

    @ZenMethod
    public float getBlockResistance() {
        return blockResistance;
    }

    @ZenMethod
    public BlockRepresentation setBlockResistance(float blockResistance) {
        this.blockResistance = blockResistance;
        return this;
    }

    @ZenMethod
    public String getToolClass() {
        return toolClass;
    }

    @ZenMethod
    public BlockRepresentation setToolClass(String toolClass) {
        this.toolClass = toolClass;
        return this;
    }

    @ZenMethod
    public int getToolLevel() {
        return toolLevel;
    }

    @ZenMethod
    public BlockRepresentation setToolLevel(int toolLevel) {
        this.toolLevel = toolLevel;
        return this;
    }

    @ZenMethod
    public ISoundTypeDefinition getBlockSoundType() {
        return this.blockSoundType;
    }

    @ZenMethod
    public BlockRepresentation setBlockSoundType(ISoundTypeDefinition blockSoundType) {
        this.blockSoundType = blockSoundType;
        return this;
    }

    @ZenMethod
    public IBlockMaterialDefinition getBlockMaterial() {
        return this.blockMaterial;
    }

    @ZenMethod
    public BlockRepresentation setBlockMaterial(IBlockMaterialDefinition material) {
        this.blockMaterial = material;
        return this;
    }

    @ZenMethod
    public BlockRepresentation setEnchantPowerBonus(float enchantPowerBonus) {
        this.enchantPowerBonus = enchantPowerBonus;
        return this;
    }

    @ZenMethod
    public float getEnchantPowerBonus() {
        return enchantPowerBonus;
    }

    @ZenMethod
    public BlockRepresentation setEnumBlockRenderType(String blockRenderType) {
        this.enumBlockRenderType = blockRenderType;
        return this;
    }

    @ZenMethod
    public String getEnumBlockRenderType() {
        return this.enumBlockRenderType;
    }

    @ZenMethod
    public BlockRepresentation setSlipperiness(float slipperiness) {
        this.slipperiness = slipperiness;
        return this;
    }

    @ZenMethod
    public float getSlipperiness() {
        return this.slipperiness;
    }

    @ZenMethod
    public BlockRepresentation setOnBlockBreak(IBlockAction iBlockAction) {
        this.onBlockBreak = iBlockAction;
        return this;
    }

    @ZenMethod
    public IBlockAction getOnBlockBreak() {
        return this.onBlockBreak;
    }

    @ZenMethod
    public BlockRepresentation setOnBlockPlace(IBlockAction iBlockAction) {
        this.onBlockPlace = iBlockAction;
        return this;
    }

    @ZenMethod
    public IBlockAction getOnBlockPlace() {
        return this.onBlockPlace;
    }

    @ZenMethod
    public BlockRepresentation setBlockLayer(String blockLayer) {
        this.blockLayer = blockLayer;
        return this;
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
    public BlockRepresentation setAxisAlignedBB(MCAxisAlignedBB axisAlignedBB) {
        this.axisAlignedBB = axisAlignedBB;
        return this;
    }

    @ZenMethod
    public IBlockAction getOnUpdateTick() {
        return onUpdateTick;
    }

    @ZenMethod
    public BlockRepresentation setOnUpdateTick(IBlockAction onUpdateTick) {
        this.onUpdateTick = onUpdateTick;
        return this;
    }

    @ZenMethod
    public IBlockAction getOnRandomTick() {
        return onRandomTick;
    }

    @ZenMethod
    public BlockRepresentation setOnRandomTick(IBlockAction onRandomTick) {
        this.onRandomTick = onRandomTick;
        return this;
    }

    @ZenMethod
    public PushReaction getMobilityFlag() {
        return mobilityFlag;
    }

    @ZenMethod
    public BlockRepresentation setMobilityFlag(PushReaction mobilityFlag) {
        this.mobilityFlag = mobilityFlag;
        return this;
    }

    @ZenMethod
    public boolean isPassable() {
        return passable;
    }

    @ZenMethod
    public BlockRepresentation setPassable(boolean passable) {
        this.passable = passable;
        return this;
    }

    @ZenMethod
    public BlockRepresentation setReplaceable(boolean replaceable) {
        this.replaceable = replaceable;
        return this;
    }

    @ZenMethod
    public boolean isReplaceable() {
        return replaceable;
    }

    @ZenMethod
    public BlockRepresentation setEntitySpawnable(boolean entitySpawnable) {
        this.entitySpawnable = entitySpawnable;
        return this;
    }

    @ZenMethod
    public boolean canEntitySpawn() {
        return entitySpawnable;
    }

    @ZenMethod
    public BlockRepresentation setWitherProof(boolean witherProof) {
        this.witherProof = witherProof;
        return this;
    }

    @ZenMethod
    public boolean isWitherProof() {
        return witherProof;
    }

    @ZenMethod
    public IBlockDropHandler getDropHandler() {
        return dropHandler;
    }

    @ZenMethod
    public BlockRepresentation setDropHandler(IBlockDropHandler dropHandler) {
        this.dropHandler = dropHandler;
        return this;
    }

    @ZenMethod
    public IBlockColorSupplier getBlockColorSupplier() {
        return blockColorSupplier;
    }

    @ZenMethod
    public BlockRepresentation setBlockColorSupplier(IBlockColorSupplier blockColorSupplier) {
        this.blockColorSupplier = blockColorSupplier;
        return this;
    }

    @ZenMethod
    public IItemColorSupplier getItemColorSupplier() {
        return itemColorSupplier;
    }

    @ZenMethod
    public BlockRepresentation setItemColorSupplier(IItemColorSupplier itemColorSupplier) {
        this.itemColorSupplier = itemColorSupplier;
        return this;
    }

    @ZenMethod
    public BlockRepresentation setBlockFaceSupplier(IBlockFaceSupplier blockFaceSupplier) {
        this.blockFaceSupplier = blockFaceSupplier;
        return this;
    }

    @ZenMethod
    public IBlockFaceSupplier getBlockFaceSupplier() {
        return this.blockFaceSupplier;
    }

    @ZenMethod
    public CTResourceLocation getTextureLocation() {
        return textureLocation;
    }

    @ZenMethod
    public BlockRepresentation setTextureLocation(CTResourceLocation resourceLocation) {
        this.textureLocation = resourceLocation;
        return this;
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
