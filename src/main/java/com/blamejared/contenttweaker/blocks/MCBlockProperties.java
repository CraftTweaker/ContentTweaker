package com.blamejared.contenttweaker.blocks;

import com.blamejared.contenttweaker.*;
import com.blamejared.contenttweaker.blocks.wrappers.*;
import com.blamejared.contenttweaker.items.*;
import com.blamejared.contenttweaker.items.wrappers.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.impl.blocks.*;
import com.blamejared.crafttweaker.impl.util.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import org.openzen.zencode.java.*;

@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.block.MCBlockProperties")
@Document("mods/contenttweaker/block/MCBlockProperties")
public class MCBlockProperties {
    
    private final Block.Properties internal;
    @ZenCodeType.Field
    public MCItemGroup itemGroup = new MCItemGroup(ItemGroup.SEARCH);
    @ZenCodeType.Field
    public int maxStackSize = 64;
    
    @ZenCodeType.Constructor
    public MCBlockProperties(@ZenCodeType.Optional("<blockmaterial:iron>") MCBlockMaterial material) {
        this(Block.Properties.create(material.getInternal()));
    }
    
    public MCBlockProperties(Block.Properties internal) {
        this.internal = internal;
    }
    
    @ZenCodeType.Method
    public static MCBlockProperties copyFrom(MCBlock block) {
        return new MCBlockProperties(Block.Properties.from(block.getInternal()));
    }
    
    public Block.Properties getInternal() {
        return internal;
    }
    
    @ZenCodeType.Method
    public MCBlockProperties doesNotBlockMovement() {
        Block.Properties set = internal.doesNotBlockMovement();
        return set == internal ? this : new MCBlockProperties(set);
    }
    
    @ZenCodeType.Method
    public MCBlockProperties withSlipperiness(float slipperinessIn) {
        Block.Properties set = internal.slipperiness(slipperinessIn);
        return set == internal ? this : new MCBlockProperties(set);
    }
    
    @ZenCodeType.Method
    public MCBlockProperties withSound(MCSoundType soundTypeIn) {
        Block.Properties set = internal.sound(soundTypeIn.getInternal());
        return set == internal ? this : new MCBlockProperties(set);
    }
    
    @ZenCodeType.Method
    public MCBlockProperties withLightValue(int lightValueIn) {
        Block.Properties set = internal.lightValue(lightValueIn);
        return set == internal ? this : new MCBlockProperties(set);
    }
    
    @ZenCodeType.Method
    public MCBlockProperties withHardnessAndResistance(float hardnessIn, float resistanceIn) {
        Block.Properties set = internal.hardnessAndResistance(hardnessIn, resistanceIn);
        return set == internal ? this : new MCBlockProperties(set);
    }
    
    @ZenCodeType.Method
    public MCBlockProperties withHardnessAndResistance(float hardnessAndResistance) {
        Block.Properties set = internal.hardnessAndResistance(hardnessAndResistance);
        return set == internal ? this : new MCBlockProperties(set);
    }
    
    @ZenCodeType.Method
    public MCBlockProperties withTickRandomly() {
        Block.Properties set = internal.tickRandomly();
        return set == internal ? this : new MCBlockProperties(set);
    }
    
    @ZenCodeType.Method
    public MCBlockProperties withVariableOpacity() {
        Block.Properties set = internal.variableOpacity();
        return set == internal ? this : new MCBlockProperties(set);
    }
    
    @ZenCodeType.Method
    public MCBlockProperties withHarvestLevel(int harvestLevel) {
        Block.Properties set = internal.harvestLevel(harvestLevel);
        return set == internal ? this : new MCBlockProperties(set);
    }
    
    @ZenCodeType.Method
    public MCBlockProperties withHarvestTool(MCToolType harvestTool) {
        Block.Properties set = internal.harvestTool(harvestTool.getInternal());
        return set == internal ? this : new MCBlockProperties(set);
    }
    
    @ZenCodeType.Method
    public MCBlockProperties withNoDrops() {
        Block.Properties set = internal.noDrops();
        return set == internal ? this : new MCBlockProperties(set);
    }
    
    @ZenCodeType.Method
    public MCBlockProperties applyLootFrom(MCBlock blockIn) {
        Block.Properties set = internal.lootFrom(blockIn.getInternal());
        return set == internal ? this : new MCBlockProperties(set);
    }
    
    @ZenCodeType.Method
    public MCBlockProperties withItemGroup(MCItemGroup itemGroup) {
        this.itemGroup = itemGroup;
        return this;
    }
    
    @ZenCodeType.Method
    public MCBlockProperties withMaxStackSize(int maxStackSize) {
        this.maxStackSize = maxStackSize;
        return this;
    }
    
    @ZenCodeType.Method
    public void build(String name) {
        final MCResourceLocation resourceLocation = new MCResourceLocation(new ResourceLocation(ContentTweaker.MOD_ID, name));
        final MCItemProperties mcItemProperties = new MCItemProperties().withMaxStackSize(maxStackSize)
                .withItemGroup(itemGroup);
        VanillaFactory.registerBlock(new CoTBlock(this), mcItemProperties, resourceLocation);
    }
}
