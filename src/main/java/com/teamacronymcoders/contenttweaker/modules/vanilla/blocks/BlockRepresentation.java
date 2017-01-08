package com.teamacronymcoders.contenttweaker.modules.vanilla.blocks;

import com.teamacronymcoders.contenttweaker.api.json.JsonRequired;
import com.teamacronymcoders.contenttweaker.api.methods.callables.ICallableMethod;
import com.teamacronymcoders.contenttweaker.api.methods.returnables.IBooleanMethod;
import com.teamacronymcoders.contenttweaker.api.methods.returnables.IFloatMethod;
import com.teamacronymcoders.contenttweaker.api.methods.returnables.IIntegerMethod;
import com.teamacronymcoders.contenttweaker.api.methods.returnables.IStringMethod;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;

import java.util.List;

public class BlockRepresentation {
    //Variables
    @JsonRequired
    private String unlocalizedName;
    private CreativeTabs creativeTabs;
    private IBooleanMethod fullBlock;
    private IIntegerMethod lightOpacity;
    private IBooleanMethod translucent;
    private IIntegerMethod lightValue;
    private Boolean useNeighborBrightness;
    private IFloatMethod blockHardness;
    private IFloatMethod blockResistance;
    private Boolean enableStats;
    private Boolean needsRandomTick;
    private SoundType blockSoundType;
    private Float blockParticleGravity;
    @JsonRequired
    private Material blockMaterial;
    private IFloatMethod slipperiness;

    //Methods
    private IBooleanMethod isFullyOpaque;
    private IBooleanMethod canEntitySpawn;
    private IBooleanMethod isBlockNormalCube;
    private IBooleanMethod causesSuffocation;
    private IBooleanMethod isPassable;
    private EnumBlockRenderType getRenderType;
    private IBooleanMethod isReplaceable;
    private IBooleanMethod isCollidable;
    private Integer tickRate;
    private List<ItemStack> getDrops;
    private BlockRenderLayer getBlockLayer;
    //TODO Setup for redstone
    private IIntegerMethod redstonePower;
    private Boolean canProvidePower;
    private Boolean canSilkTouch;
    private Boolean canSpawnInBlock;
    private EnumPushReaction mobilityFlag;
    private IBooleanMethod canDropFromExplosion;
    //TODO Setup for comparator
    private IIntegerMethod comparatorLevel;
    private Boolean hasComparatorInputOverried;
    private Boolean isLadder;
    private IBooleanMethod isBurning;
    private Boolean isAir;
    private IIntegerMethod flammability;
    private IIntegerMethod getFireSpreadSpeed;
    private Boolean fireSource;
    private Boolean canCreatureSpawn;
    private Boolean isBed;
    private Boolean canSustainLeaves;
    private Boolean canBeReplacedByLeaves;
    private Boolean isReplaceableOreGen;
    private Boolean canConnectRedstone;
    private ItemStack getPickBlock;
    private Boolean canSustainPlant;
    private IBooleanMethod isFertile;
    private Boolean canEntityDestroy;
    private Boolean isBeaconBase;
    private IFloatMethod getEnchantPowerBonus;
    private IIntegerMethod expDrop;
    private float[] getBeaconColorMultiplier;
    private List<String> information;
    private IIntegerMethod harvestLevel;
    private IStringMethod harvestTool;

    //Callable Methods
    private ICallableMethod randomTick;
    private ICallableMethod randomDisplayTick;
    private ICallableMethod onBlockDestroyedByPlayer;
    private ICallableMethod onBlockAdded;
    private ICallableMethod breakBlock;
    private ICallableMethod onBlockDestroyedByExplosion;
    private ICallableMethod onBlockActivated;
    private ICallableMethod onEntityWalk;
    private ICallableMethod onBlockClicked;
    private ICallableMethod onBlockPlacedBy;
    private ICallableMethod onFallenUpon;
    private ICallableMethod onLanded;
    private ICallableMethod onBlockHarvested;
    private ICallableMethod onFillWithRain;
    private ICallableMethod onBlockExploded;
    private ICallableMethod onPlantGrow;
    private ICallableMethod onNeighborChange;

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

    public IBooleanMethod getFullBlock() {
        return fullBlock;
    }

    public void setFullBlock(IBooleanMethod fullBlock) {
        this.fullBlock = fullBlock;
    }

    public IIntegerMethod getLightOpacity() {
        return lightOpacity;
    }

    public void setLightOpacity(IIntegerMethod lightOpacity) {
        this.lightOpacity = lightOpacity;
    }

    public IBooleanMethod getTranslucent() {
        return translucent;
    }

    public void setTranslucent(IBooleanMethod translucent) {
        this.translucent = translucent;
    }

    public IIntegerMethod getLightValue() {
        return lightValue;
    }

    public void setLightValue(IIntegerMethod lightValue) {
        this.lightValue = lightValue;
    }

    public Boolean getUseNeighborBrightness() {
        return useNeighborBrightness;
    }

    public void setUseNeighborBrightness(Boolean useNeighborBrightness) {
        this.useNeighborBrightness = useNeighborBrightness;
    }

    public IFloatMethod getBlockHardness() {
        return blockHardness;
    }

    public void setBlockHardness(IFloatMethod blockHardness) {
        this.blockHardness = blockHardness;
    }

    public IFloatMethod getBlockResistance() {
        return blockResistance;
    }

    public void setBlockResistance(IFloatMethod blockResistance) {
        this.blockResistance = blockResistance;
    }

    public Boolean getEnableStats() {
        return enableStats;
    }

    public void setEnableStats(Boolean enableStats) {
        this.enableStats = enableStats;
    }

    public Boolean getNeedsRandomTick() {
        return needsRandomTick;
    }

    public void setNeedsRandomTick(Boolean needsRandomTick) {
        this.needsRandomTick = needsRandomTick;
    }

    public SoundType getBlockSoundType() {
        return blockSoundType;
    }

    public void setBlockSoundType(SoundType blockSoundType) {
        this.blockSoundType = blockSoundType;
    }

    public Float getBlockParticleGravity() {
        return blockParticleGravity;
    }

    public void setBlockParticleGravity(Float blockParticleGravity) {
        this.blockParticleGravity = blockParticleGravity;
    }

    public Material getBlockMaterial() {
        return blockMaterial;
    }

    public void setBlockMaterial(Material blockMaterial) {
        this.blockMaterial = blockMaterial;
    }

    public IFloatMethod getSlipperiness() {
        return slipperiness;
    }

    public void setSlipperiness(IFloatMethod slipperiness) {
        this.slipperiness = slipperiness;
    }

    public IBooleanMethod getIsFullyOpaque() {
        return isFullyOpaque;
    }

    public void setIsFullyOpaque(IBooleanMethod isFullyOpaque) {
        this.isFullyOpaque = isFullyOpaque;
    }

    public IBooleanMethod getCanEntitySpawn() {
        return canEntitySpawn;
    }

    public void setCanEntitySpawn(IBooleanMethod canEntitySpawn) {
        this.canEntitySpawn = canEntitySpawn;
    }

    public IBooleanMethod getIsBlockNormalCube() {
        return isBlockNormalCube;
    }

    public void setIsBlockNormalCube(IBooleanMethod isBlockNormalCube) {
        this.isBlockNormalCube = isBlockNormalCube;
    }

    public IBooleanMethod getCausesSuffocation() {
        return causesSuffocation;
    }

    public void setCausesSuffocation(IBooleanMethod causesSuffocation) {
        this.causesSuffocation = causesSuffocation;
    }

    public IBooleanMethod getIsPassable() {
        return isPassable;
    }

    public void setIsPassable(IBooleanMethod isPassable) {
        this.isPassable = isPassable;
    }

    public EnumBlockRenderType getGetRenderType() {
        return getRenderType;
    }

    public void setGetRenderType(EnumBlockRenderType getRenderType) {
        this.getRenderType = getRenderType;
    }

    public IBooleanMethod getIsReplaceable() {
        return isReplaceable;
    }

    public void setIsReplaceable(IBooleanMethod isReplaceable) {
        this.isReplaceable = isReplaceable;
    }

    public IBooleanMethod getIsCollidable() {
        return isCollidable;
    }

    public void setIsCollidable(IBooleanMethod isCollidable) {
        this.isCollidable = isCollidable;
    }

    public Integer getTickRate() {
        return tickRate;
    }

    public void setTickRate(Integer tickRate) {
        this.tickRate = tickRate;
    }

    public List<ItemStack> getGetDrops() {
        return getDrops;
    }

    public void setGetDrops(List<ItemStack> getDrops) {
        this.getDrops = getDrops;
    }

    public BlockRenderLayer getGetBlockLayer() {
        return getBlockLayer;
    }

    public void setGetBlockLayer(BlockRenderLayer getBlockLayer) {
        this.getBlockLayer = getBlockLayer;
    }

    public IIntegerMethod getRedstonePower() {
        return redstonePower;
    }

    public void setRedstonePower(IIntegerMethod redstonePower) {
        this.redstonePower = redstonePower;
    }

    public Boolean getCanProvidePower() {
        return canProvidePower;
    }

    public void setCanProvidePower(Boolean canProvidePower) {
        this.canProvidePower = canProvidePower;
    }

    public Boolean getCanSilkTouch() {
        return canSilkTouch;
    }

    public void setCanSilkTouch(Boolean canSilkTouch) {
        this.canSilkTouch = canSilkTouch;
    }

    public Boolean getCanSpawnInBlock() {
        return canSpawnInBlock;
    }

    public void setCanSpawnInBlock(Boolean canSpawnInBlock) {
        this.canSpawnInBlock = canSpawnInBlock;
    }

    public EnumPushReaction getMobilityFlag() {
        return mobilityFlag;
    }

    public void setMobilityFlag(EnumPushReaction mobilityFlag) {
        this.mobilityFlag = mobilityFlag;
    }

    public IBooleanMethod getCanDropFromExplosion() {
        return canDropFromExplosion;
    }

    public void setCanDropFromExplosion(IBooleanMethod canDropFromExplosion) {
        this.canDropFromExplosion = canDropFromExplosion;
    }

    public IIntegerMethod getComparatorLevel() {
        return comparatorLevel;
    }

    public void setComparatorLevel(IIntegerMethod comparatorLevel) {
        this.comparatorLevel = comparatorLevel;
    }

    public Boolean getHasComparatorInputOverried() {
        return hasComparatorInputOverried;
    }

    public void setHasComparatorInputOverried(Boolean hasComparatorInputOverried) {
        this.hasComparatorInputOverried = hasComparatorInputOverried;
    }

    public Boolean getLadder() {
        return isLadder;
    }

    public void setLadder(Boolean ladder) {
        isLadder = ladder;
    }

    public IBooleanMethod getIsBurning() {
        return isBurning;
    }

    public void setIsBurning(IBooleanMethod isBurning) {
        this.isBurning = isBurning;
    }

    public Boolean getAir() {
        return isAir;
    }

    public void setAir(Boolean air) {
        isAir = air;
    }

    public IIntegerMethod getFlammability() {
        return flammability;
    }

    public void setFlammability(IIntegerMethod flammability) {
        this.flammability = flammability;
    }

    public IIntegerMethod getGetFireSpreadSpeed() {
        return getFireSpreadSpeed;
    }

    public void setGetFireSpreadSpeed(IIntegerMethod getFireSpreadSpeed) {
        this.getFireSpreadSpeed = getFireSpreadSpeed;
    }

    public Boolean getFireSource() {
        return fireSource;
    }

    public void setFireSource(Boolean fireSource) {
        this.fireSource = fireSource;
    }

    public Boolean getCanCreatureSpawn() {
        return canCreatureSpawn;
    }

    public void setCanCreatureSpawn(Boolean canCreatureSpawn) {
        this.canCreatureSpawn = canCreatureSpawn;
    }

    public Boolean getBed() {
        return isBed;
    }

    public void setBed(Boolean bed) {
        isBed = bed;
    }

    public Boolean getCanSustainLeaves() {
        return canSustainLeaves;
    }

    public void setCanSustainLeaves(Boolean canSustainLeaves) {
        this.canSustainLeaves = canSustainLeaves;
    }

    public Boolean getCanBeReplacedByLeaves() {
        return canBeReplacedByLeaves;
    }

    public void setCanBeReplacedByLeaves(Boolean canBeReplacedByLeaves) {
        this.canBeReplacedByLeaves = canBeReplacedByLeaves;
    }

    public Boolean getReplaceableOreGen() {
        return isReplaceableOreGen;
    }

    public void setReplaceableOreGen(Boolean replaceableOreGen) {
        isReplaceableOreGen = replaceableOreGen;
    }

    public Boolean getCanConnectRedstone() {
        return canConnectRedstone;
    }

    public void setCanConnectRedstone(Boolean canConnectRedstone) {
        this.canConnectRedstone = canConnectRedstone;
    }

    public ItemStack getGetPickBlock() {
        return getPickBlock;
    }

    public void setGetPickBlock(ItemStack getPickBlock) {
        this.getPickBlock = getPickBlock;
    }

    public Boolean getCanSustainPlant() {
        return canSustainPlant;
    }

    public void setCanSustainPlant(Boolean canSustainPlant) {
        this.canSustainPlant = canSustainPlant;
    }

    public IBooleanMethod getIsFertile() {
        return isFertile;
    }

    public void setIsFertile(IBooleanMethod isFertile) {
        this.isFertile = isFertile;
    }

    public Boolean getCanEntityDestroy() {
        return canEntityDestroy;
    }

    public void setCanEntityDestroy(Boolean canEntityDestroy) {
        this.canEntityDestroy = canEntityDestroy;
    }

    public Boolean getBeaconBase() {
        return isBeaconBase;
    }

    public void setBeaconBase(Boolean beaconBase) {
        isBeaconBase = beaconBase;
    }

    public IFloatMethod getGetEnchantPowerBonus() {
        return getEnchantPowerBonus;
    }

    public void setGetEnchantPowerBonus(IFloatMethod getEnchantPowerBonus) {
        this.getEnchantPowerBonus = getEnchantPowerBonus;
    }

    public IIntegerMethod getExpDrop() {
        return expDrop;
    }

    public void setExpDrop(IIntegerMethod expDrop) {
        this.expDrop = expDrop;
    }

    public float[] getGetBeaconColorMultiplier() {
        return getBeaconColorMultiplier;
    }

    public void setGetBeaconColorMultiplier(float[] getBeaconColorMultiplier) {
        this.getBeaconColorMultiplier = getBeaconColorMultiplier;
    }

    public List<String> getInformation() {
        return information;
    }

    public void setInformation(List<String> information) {
        this.information = information;
    }

    public IIntegerMethod getHarvestLevel() {
        return harvestLevel;
    }

    public void setHarvestLevel(IIntegerMethod harvestLevel) {
        this.harvestLevel = harvestLevel;
    }

    public IStringMethod getHarvestTool() {
        return harvestTool;
    }

    public void setHarvestTool(IStringMethod harvestTool) {
        this.harvestTool = harvestTool;
    }

    public ICallableMethod getRandomTick() {
        return randomTick;
    }

    public void setRandomTick(ICallableMethod randomTick) {
        this.randomTick = randomTick;
    }

    public ICallableMethod getRandomDisplayTick() {
        return randomDisplayTick;
    }

    public void setRandomDisplayTick(ICallableMethod randomDisplayTick) {
        this.randomDisplayTick = randomDisplayTick;
    }

    public ICallableMethod getOnBlockDestroyedByPlayer() {
        return onBlockDestroyedByPlayer;
    }

    public void setOnBlockDestroyedByPlayer(ICallableMethod onBlockDestroyedByPlayer) {
        this.onBlockDestroyedByPlayer = onBlockDestroyedByPlayer;
    }

    public ICallableMethod getOnBlockAdded() {
        return onBlockAdded;
    }

    public void setOnBlockAdded(ICallableMethod onBlockAdded) {
        this.onBlockAdded = onBlockAdded;
    }

    public ICallableMethod getBreakBlock() {
        return breakBlock;
    }

    public void setBreakBlock(ICallableMethod breakBlock) {
        this.breakBlock = breakBlock;
    }

    public ICallableMethod getOnBlockDestroyedByExplosion() {
        return onBlockDestroyedByExplosion;
    }

    public void setOnBlockDestroyedByExplosion(ICallableMethod onBlockDestroyedByExplosion) {
        this.onBlockDestroyedByExplosion = onBlockDestroyedByExplosion;
    }

    public ICallableMethod getOnBlockActivated() {
        return onBlockActivated;
    }

    public void setOnBlockActivated(ICallableMethod onBlockActivated) {
        this.onBlockActivated = onBlockActivated;
    }

    public ICallableMethod getOnEntityWalk() {
        return onEntityWalk;
    }

    public void setOnEntityWalk(ICallableMethod onEntityWalk) {
        this.onEntityWalk = onEntityWalk;
    }

    public ICallableMethod getOnBlockClicked() {
        return onBlockClicked;
    }

    public void setOnBlockClicked(ICallableMethod onBlockClicked) {
        this.onBlockClicked = onBlockClicked;
    }

    public ICallableMethod getOnBlockPlacedBy() {
        return onBlockPlacedBy;
    }

    public void setOnBlockPlacedBy(ICallableMethod onBlockPlacedBy) {
        this.onBlockPlacedBy = onBlockPlacedBy;
    }

    public ICallableMethod getOnFallenUpon() {
        return onFallenUpon;
    }

    public void setOnFallenUpon(ICallableMethod onFallenUpon) {
        this.onFallenUpon = onFallenUpon;
    }

    public ICallableMethod getOnLanded() {
        return onLanded;
    }

    public void setOnLanded(ICallableMethod onLanded) {
        this.onLanded = onLanded;
    }

    public ICallableMethod getOnBlockHarvested() {
        return onBlockHarvested;
    }

    public void setOnBlockHarvested(ICallableMethod onBlockHarvested) {
        this.onBlockHarvested = onBlockHarvested;
    }

    public ICallableMethod getOnFillWithRain() {
        return onFillWithRain;
    }

    public void setOnFillWithRain(ICallableMethod onFillWithRain) {
        this.onFillWithRain = onFillWithRain;
    }

    public ICallableMethod getOnBlockExploded() {
        return onBlockExploded;
    }

    public void setOnBlockExploded(ICallableMethod onBlockExploded) {
        this.onBlockExploded = onBlockExploded;
    }

    public ICallableMethod getOnPlantGrow() {
        return onPlantGrow;
    }

    public void setOnPlantGrow(ICallableMethod onPlantGrow) {
        this.onPlantGrow = onPlantGrow;
    }

    public ICallableMethod getOnNeighborChange() {
        return onNeighborChange;
    }

    public void setOnNeighborChange(ICallableMethod onNeighborChange) {
        this.onNeighborChange = onNeighborChange;
    }
}
