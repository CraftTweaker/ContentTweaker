package com.blamejared.contenttweaker.blocks;

import com.blamejared.contenttweaker.api.*;
import com.blamejared.contenttweaker.api.blocks.*;
import com.blamejared.contenttweaker.blocks.types.*;
import com.blamejared.contenttweaker.wrappers.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.impl.block.material.*;
import com.blamejared.crafttweaker.impl.blocks.*;
import com.blamejared.crafttweaker.impl.util.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import org.openzen.zencode.java.*;

@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.block.BlockBuilder")
public class BlockBuilder implements IIsBuilder {
    
    private final Block.Properties blockProperties;
    private final Item.Properties itemProperties;
    
    @ZenCodeType.Constructor
    public BlockBuilder(@ZenCodeType.Optional("<blockmaterial:iron>") MCMaterial material) {
        blockProperties = Block.Properties.create(material.getInternal());
        itemProperties = new Item.Properties().group(ItemGroup.MISC);
    }
    
    public Item.Properties getItemProperties() {
        return itemProperties;
    }
    
    public Block.Properties getBlockProperties() {
        return blockProperties;
    }
    
    @ZenCodeType.Method
    public BlockBuilder withMaxStackSize(int size) {
        this.itemProperties.maxStackSize(size);
        return this;
    }
    
    @ZenCodeType.Method
    public BlockBuilder withItemGroup(MCItemGroup group) {
        this.itemProperties.group(group.getInternal());
        return this;
    }
    
    @ZenCodeType.Method
    public BlockBuilder withRarity(String rarity) {
        this.itemProperties.rarity(Rarity.valueOf(rarity));
        return this;
    }
    
    @ZenCodeType.Method
    public BlockBuilder withoutMovementBlocking() {
        this.blockProperties.doesNotBlockMovement();
        return this;
    }
    
    @ZenCodeType.Method
    public BlockBuilder withSlipperiness(float slipperinessIn) {
        blockProperties.slipperiness(slipperinessIn);
        return this;
    }
    
    @ZenCodeType.Method
    public BlockBuilder withLightValue(int lightValueIn) {
        blockProperties.lightValue(lightValueIn);
        return this;
    }
    
    @ZenCodeType.Method
    public BlockBuilder withHardnessAndResistance(float hardnessIn, float resistanceIn) {
        blockProperties.hardnessAndResistance(hardnessIn, resistanceIn);
        return this;
    }
    
    @ZenCodeType.Method
    public BlockBuilder withHardnessAndResistance(float hardnessAndResistance) {
        blockProperties.hardnessAndResistance(hardnessAndResistance);
        return this;
    }
    
    @ZenCodeType.Method
    public BlockBuilder withTickRandomly() {
        blockProperties.tickRandomly();
        return this;
    }
    
    @ZenCodeType.Method
    public BlockBuilder withVariableOpacity() {
        blockProperties.variableOpacity();
        return this;
    }
    
    @ZenCodeType.Method
    public BlockBuilder withHarvestLevel(int harvestLevel) {
        blockProperties.harvestLevel(harvestLevel);
        return this;
    }
    
    @ZenCodeType.Method
    public BlockBuilder withHarvestTool(MCToolType harvestTool) {
        blockProperties.harvestTool(harvestTool.getInternal());
        return this;
    }
    
    @ZenCodeType.Method
    public BlockBuilder withoutDrops() {
        blockProperties.noDrops();
        return this;
    }
    
    @ZenCodeType.Method
    public BlockBuilder withLootFrom(MCBlock blockIn) {
        blockProperties.lootFrom(blockIn.getInternal());
        return this;
    }
    
    @ZenCodeType.Method
    public <T extends IIsBuilder> T withType(IBlockTypeSpecifier<T> specifier) {
        return specifier.apply(this);
    }
    
    @Override
    public void build(MCResourceLocation location) {
        withType(BlockTypeSpecifiers.basic).build(location);
    }
}
