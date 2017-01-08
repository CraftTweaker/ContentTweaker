package com.teamacronymcoders.contenttweaker.modules.vanilla.blocks;

import com.teamacronymcoders.contenttweaker.api.json.JsonRequired;
import com.teamacronymcoders.contenttweaker.api.methods.ICallableMethod;
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
    private Boolean fullBlock;
    private Integer lightOpacity;
    private Boolean translucent;
    private Integer lightValue;
    private Boolean useNeighborBrightness;
    private Float blockHardness;
    private Float blockResistance;
    private Boolean enableStats;
    private Boolean needsRandomTick;
    private SoundType blockSoundType;
    private Float blockParticleGravity;
    @JsonRequired
    private Material blockMaterial;
    private Float slipperiness;

    //Methods
    private Boolean isFullyOpaque;
    private Boolean canEntitySpawn;
    private Boolean isBlockNormalCube;
    private Boolean causesSuffocation;
    private Boolean isPassable;
    private EnumBlockRenderType getRenderType;
    private Boolean isReplaceable;
    private Boolean isCollidable;
    private Integer tickRate;
    private List<ItemStack> getDrops;
    private BlockRenderLayer getBlockLayer;
    //TODO Setup for redstone
    private Integer redstonePower;
    private Boolean canProvidePower;
    private Boolean canSilkTouch;
    private Boolean canSpawnInBlock;
    private EnumPushReaction mobilityFlag;
    private Boolean canDropFromExplosion;
    //TODO Setup for comparator
    private Integer comparatorLevel;
    private Boolean hasComparatorInputOverried;
    private Boolean isLadder;
    private Boolean isBurning;
    private Boolean isAir;
    private Integer flammability;
    private Integer getFireSpreadSpeed;
    private Boolean fireSource;
    private Boolean canCreatureSpawn;
    private Boolean isBed;
    private Boolean canSustainLeaves;
    private Boolean canBeReplacedByLeaves;
    private Boolean isReplaceableOreGen;
    private Boolean canConnectRedstone;
    private ItemStack getPickBlock;
    private Boolean canSustainPlant;
    private Boolean isFertile;
    private Boolean canEntityDestroy;
    private Boolean isBeaconBase;
    private Float getEnchantPowerBonus;
    private Integer expDrop;
    private float[] getBeaconColorMultiplier;
    private List<String> information;
    private Integer harvestLevel;
    private String harvestTool;

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
}
