package com.teamacronymcoders.tailoredobjects.representations.blocks;

import com.teamacronymcoders.tailoredobjects.representations.items.ItemStackRepresentation;
import com.teamacronymcoders.tailoredobjects.representations.tileentities.TileEntityRepresentation;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.item.ItemStack;

import java.util.List;

public class BlockRepresentation {
    private String localizedName;
    private String unlocalizedName;
    private float slipperiness;
    private boolean isPassable;
    private boolean isRepleacable;
    private int blockHardness;
    private boolean isCollidable;
    private int tickRate;
    private int weakPower;
    private boolean canProvidePower;
    private int strongPower;
    private boolean hasStats;
    private EnumPushReaction mobilityFlag;
    private String creativeTab;
    private int comparatorValue;
    private int getLightValue;
    private boolean isLadder;
    private boolean isBurning;
    private boolean isAir;
    private boolean canHarvestBlock;
    private int flammability;
    private boolean isFlammable;
    private int fireSpreadSpeed;
    private boolean isFireSource;
    private boolean canCreatureSpawn;
    private boolean isBed;
    private float explosionResistance;
    private boolean canSustainPlant;
    private boolean isFertile;
    private int lightOpacity;
    private boolean isBeaconBase;
    private float enchantPowerBonus;
    private int experienceDropped;
    private List<String> information;
    private String soundType;
    private float[] beaconColorMultiplier;
    private List<String> dropNames;
    private List<ItemStackRepresentation> drops;
    private List<ItemStack> itemStackDrops;
    private String commandName;
    private BlockCommandRepresentation commands;
    private String tileEntityName;
    private TileEntityRepresentation tileEntity;

    public float getSlipperiness() {
        return slipperiness;
    }

    public void setSlipperiness(float slipperiness) {
        this.slipperiness = slipperiness;
    }

    public boolean isPassable() {
        return isPassable;
    }

    public void setPassable(boolean passable) {
        isPassable = passable;
    }

    public boolean isRepleacable() {
        return isRepleacable;
    }

    public void setRepleacable(boolean repleacable) {
        isRepleacable = repleacable;
    }

    public int getBlockHardness() {
        return blockHardness;
    }

    public void setBlockHardness(int blockHardness) {
        this.blockHardness = blockHardness;
    }

    public boolean isCollidable() {
        return isCollidable;
    }

    public void setCollidable(boolean collidable) {
        isCollidable = collidable;
    }

    public int getTickRate() {
        return tickRate;
    }

    public void setTickRate(int tickRate) {
        this.tickRate = tickRate;
    }

    public int getWeakPower() {
        return weakPower;
    }

    public void setWeakPower(int weakPower) {
        this.weakPower = weakPower;
    }

    public boolean isCanProvidePower() {
        return canProvidePower;
    }

    public void setCanProvidePower(boolean canProvidePower) {
        this.canProvidePower = canProvidePower;
    }

    public int getStrongPower() {
        return strongPower;
    }

    public void setStrongPower(int strongPower) {
        this.strongPower = strongPower;
    }

    public boolean isHasStats() {
        return hasStats;
    }

    public void setHasStats(boolean hasStats) {
        this.hasStats = hasStats;
    }

    public EnumPushReaction getMobilityFlag() {
        return mobilityFlag;
    }

    public void setMobilityFlag(EnumPushReaction mobilityFlag) {
        this.mobilityFlag = mobilityFlag;
    }

    public String getCreativeTab() {
        return creativeTab;
    }

    public void setCreativeTab(String creativeTab) {
        this.creativeTab = creativeTab;
    }

    public int getComparatorValue() {
        return comparatorValue;
    }

    public void setComparatorValue(int comparatorValue) {
        this.comparatorValue = comparatorValue;
    }

    public int getGetLightValue() {
        return getLightValue;
    }

    public void setGetLightValue(int getLightValue) {
        this.getLightValue = getLightValue;
    }

    public boolean isLadder() {
        return isLadder;
    }

    public void setLadder(boolean ladder) {
        isLadder = ladder;
    }

    public boolean isBurning() {
        return isBurning;
    }

    public void setBurning(boolean burning) {
        isBurning = burning;
    }

    public boolean isAir() {
        return isAir;
    }

    public void setAir(boolean air) {
        isAir = air;
    }

    public boolean isCanHarvestBlock() {
        return canHarvestBlock;
    }

    public void setCanHarvestBlock(boolean canHarvestBlock) {
        this.canHarvestBlock = canHarvestBlock;
    }

    public int getFlammability() {
        return flammability;
    }

    public void setFlammability(int flammability) {
        this.flammability = flammability;
    }

    public boolean isFlammable() {
        return isFlammable;
    }

    public void setFlammable(boolean flammable) {
        isFlammable = flammable;
    }

    public int getFireSpreadSpeed() {
        return fireSpreadSpeed;
    }

    public void setFireSpreadSpeed(int fireSpreadSpeed) {
        this.fireSpreadSpeed = fireSpreadSpeed;
    }

    public boolean isFireSource() {
        return isFireSource;
    }

    public void setFireSource(boolean fireSource) {
        isFireSource = fireSource;
    }

    public boolean isCanCreatureSpawn() {
        return canCreatureSpawn;
    }

    public void setCanCreatureSpawn(boolean canCreatureSpawn) {
        this.canCreatureSpawn = canCreatureSpawn;
    }

    public boolean isBed() {
        return isBed;
    }

    public void setBed(boolean bed) {
        isBed = bed;
    }

    public float getExplosionResistance() {
        return explosionResistance;
    }

    public void setExplosionResistance(float explosionResistance) {
        this.explosionResistance = explosionResistance;
    }

    public boolean isCanSustainPlant() {
        return canSustainPlant;
    }

    public void setCanSustainPlant(boolean canSustainPlant) {
        this.canSustainPlant = canSustainPlant;
    }

    public boolean isFertile() {
        return isFertile;
    }

    public void setFertile(boolean fertile) {
        isFertile = fertile;
    }

    public int getLightOpacity() {
        return lightOpacity;
    }

    public void setLightOpacity(int lightOpacity) {
        this.lightOpacity = lightOpacity;
    }

    public boolean isBeaconBase() {
        return isBeaconBase;
    }

    public void setBeaconBase(boolean beaconBase) {
        isBeaconBase = beaconBase;
    }

    public float getEnchantPowerBonus() {
        return enchantPowerBonus;
    }

    public void setEnchantPowerBonus(float enchantPowerBonus) {
        this.enchantPowerBonus = enchantPowerBonus;
    }

    public int getExperienceDropped() {
        return experienceDropped;
    }

    public void setExperienceDropped(int experienceDropped) {
        this.experienceDropped = experienceDropped;
    }

    public List<String> getInformation() {
        return information;
    }

    public void setInformation(List<String> information) {
        this.information = information;
    }

    public String getSoundType() {
        return soundType;
    }

    public void setSoundType(String soundType) {
        this.soundType = soundType;
    }

    public float[] getBeaconColorMultiplier() {
        return beaconColorMultiplier;
    }

    public void setBeaconColorMultiplier(float[] beaconColorMultiplier) {
        this.beaconColorMultiplier = beaconColorMultiplier;
    }

    public BlockCommandRepresentation getCommands() {
        return commands;
    }

    public void setCommands(BlockCommandRepresentation commands) {
        this.commands = commands;
    }

    public TileEntityRepresentation getTileEntity() {
        return tileEntity;
    }

    public void setTileEntity(TileEntityRepresentation tileEntity) {
        this.tileEntity = tileEntity;
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getLocalizedName() {
        return localizedName;
    }

    public void setLocalizedName(String localizedName) {
        this.localizedName = localizedName;
    }

    public String getUnlocalizedName() {
        return unlocalizedName;
    }

    public void setUnlocalizedName(String unlocalizedName) {
        this.unlocalizedName = unlocalizedName;
    }

    public String getTileEntityName() {
        return tileEntityName;
    }

    public void setTileEntityName(String tileEntityName) {
        this.tileEntityName = tileEntityName;
    }

    public List<String> getDropNames() {
        return dropNames;
    }

    public void setDropNames(List<String> dropNames) {
        this.dropNames = dropNames;
    }

    public List<ItemStackRepresentation> getDrops() {
        return drops;
    }

    public void setDrops(List<ItemStackRepresentation> drops) {
        this.drops = drops;
    }

    public List<ItemStack> getItemStackDrops() {
        return itemStackDrops;
    }

    public void setItemStackDrops(List<ItemStack> itemStackDrops) {
        this.itemStackDrops = itemStackDrops;
    }
}
