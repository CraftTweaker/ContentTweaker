package com.blamejared.contenttweaker.blocks;

import com.blamejared.contenttweaker.api.*;
import com.blamejared.contenttweaker.api.blocks.*;
import com.blamejared.contenttweaker.blocks.types.basic.*;
import com.blamejared.contenttweaker.wrappers.*;
import com.blamejared.crafttweaker.api.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import org.openzen.zencode.java.*;

import java.lang.reflect.*;

/**
 * The blockbuilder is used to... build blocks (you totally didn't see that one coming, right... right?).<br>
 * Once you created it you can set various properties which will be outlined by the separate methods.
 * <p>
 * You can also change the block's type to create a more specialized form of block (e.g. stairs or Blocks that can be rotated in the same way logs can).
 * To tell CoT that you want the block to appear ingame you need to call {@link #build(String)} and specify a valid resource location path.
 */
@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.block.BlockBuilder")
@Document("mods/contenttweaker/API/block/BlockBuilder")
public class BlockBuilder implements IIsBuilder {
    
    private final Block.Properties blockProperties;
    private final Item.Properties itemProperties;
    public boolean allowTinted;
    
    /**
     * Creates a new BlockBuilder.
     * Remember that this will _not_ create a new block in the game, you need to call {@link #build(String)} for that.
     *
     * @param material The material this block will have
     * @docParam material <blockmaterial:earth>
     */
    @ZenCodeType.Constructor
    public BlockBuilder(@ZenCodeType.Optional("<blockmaterial:iron>") Material material) {
        blockProperties = Block.Properties.create(material);
        itemProperties = new Item.Properties().group(ItemGroup.MISC);
    }
    
    @ZenCodeType.Constructor
    public BlockBuilder(Block block) {
        blockProperties = Block.Properties.from(block);
        
        //Uses ItemStack, since the other methods getters are deprecated
        final Item asItem = block.asItem();
        final ItemStack itemStack = new ItemStack(asItem);
        itemProperties = new Item.Properties();
        itemProperties.rarity(itemStack.getRarity());
        if(itemStack.isDamageable()) {
            itemProperties.maxDamage(itemStack.getMaxDamage());
        } else {
            itemProperties.maxStackSize(itemStack.getMaxStackSize());
        }
        if(itemStack.hasContainerItem()) {
            itemProperties.containerItem(itemStack.getContainerItem().getItem());
        }
        
        final ItemGroup group = asItem.getGroup();
        if(group != null) {
            itemProperties.group(group);
        }
    }
    
    public Item.Properties getItemProperties() {
        return itemProperties;
    }
    
    public Block.Properties getBlockProperties() {
        return blockProperties;
    }
    
    /**
     * Sets the maximum Stack size that this block can have when in your inventory.
     * Will be 64 if unchanged.
     *
     * @param size The size to set.
     * @return This builder, used for chaining
     * @docParam size 16
     */
    @ZenCodeType.Method
    public BlockBuilder withMaxStackSize(int size) {
        this.itemProperties.maxStackSize(size);
        return this;
    }
    
    /**
     * Sets the item group in which this block will appear
     *
     * @param group The group to set
     * @return This builder, used for method chaining
     * @docParam group <itemgroup:building_blocks>
     */
    @ZenCodeType.Method
    public BlockBuilder withItemGroup(MCItemGroup group) {
        this.itemProperties.group(group.getInternal());
        return this;
    }
    
    /**
     * Allows you to set the rarity of this block.
     *
     * @param rarity The rarity
     * @return This builder, used for method chaining
     * @docParam rarity "UNCOMMON"
     */
    @ZenCodeType.Method
    public BlockBuilder withRarity(String rarity) {
        this.itemProperties.rarity(Rarity.valueOf(rarity));
        return this;
    }
    
    /**
     * Instructs CoT that this block will does not block movement.
     * Will also set the block as {@link #notSolid()}
     *
     * @return This builder, used for chaining
     */
    @ZenCodeType.Method
    public BlockBuilder withoutMovementBlocking() {
        this.blockProperties.doesNotBlockMovement();
        return this;
    }
    
    /**
     * Instructs CoT that this block is not solid.
     * <p>
     * This is required if your model is not a full block (16x16x16).
     * It is also required if your model is see-through (like glass).
     * Set this if your block creates some X-Ray effects when it's placed.
     *
     * @return This builder, used for chaining
     */
    @ZenCodeType.Method
    public BlockBuilder notSolid() {
        this.blockProperties.notSolid();
        return this;
    }
    
    /**
     * Sets the slipperiness.
     *
     * @param slipperinessIn The value to set
     * @return This builder, used for method chaining
     * @docParam slipperinessIn 0.5f
     */
    @ZenCodeType.Method
    public BlockBuilder withSlipperiness(float slipperinessIn) {
        blockProperties.slipperiness(slipperinessIn);
        return this;
    }
    
    /**
     * Sets the block's light value.
     *
     * @param lightValueIn The light level to set
     * @return This builder, used for method chaining
     * @docParam lightValueIn 15
     */
    @ZenCodeType.Method
    public BlockBuilder withLightValue(int lightValueIn) {
        blockProperties.setLightLevel(ignored -> lightValueIn);
        return this;
    }
    
    /**
     * Sets the block's hardness and resistance levels.
     * Unlike the other method, this one allows you to set each property one to a separate value.
     *
     * @param hardnessIn   The value to set for hardness
     * @param resistanceIn The value to set for resistance.
     * @return This builder, used for method chaining
     * @docParam hardnessIn 0.5f
     * @docParam resistanceIn 0.5f
     */
    @ZenCodeType.Method
    public BlockBuilder withHardnessAndResistance(float hardnessIn, float resistanceIn) {
        blockProperties.hardnessAndResistance(hardnessIn, resistanceIn);
        return this;
    }
    
    /**
     * Sets the block's hardness and resistance levels.
     * Unlike the other method, this one only accepts one parameter and will use that value for both properties.
     *
     * @param hardnessAndResistance The value to set for hardness and for resistance.
     * @return This builder, used for method chaining
     * @docParam hardnessAndResistance 0.5f
     */
    @ZenCodeType.Method
    public BlockBuilder withHardnessAndResistance(float hardnessAndResistance) {
        blockProperties.hardnessAndResistance(hardnessAndResistance);
        return this;
    }


    /**
     * Sets the block is of a type that needs random ticking.
     * @return The builder, used for method chaining.
     */
    @ZenCodeType.Method
    public BlockBuilder withTickRandomly() {
        blockProperties.tickRandomly();
        return this;
    }
    
    /**
     * Sets the mining level required to mine this block
     *
     * @param harvestLevel The harvest level requried
     * @return This builder, used for method chaining
     * @docParam harvestLevel 3
     */
    @ZenCodeType.Method
    public BlockBuilder withHarvestLevel(int harvestLevel) {
        blockProperties.harvestLevel(harvestLevel);
        return this;
    }
    
    /**
     * Sets the tool required to harvest this block
     *
     * @param harvestTool The tool type
     * @return This builder, used for method chaining
     * @docParam harvestTool <tooltype:shovel>
     */
    @ZenCodeType.Method
    public BlockBuilder withHarvestTool(MCToolType harvestTool) {
        blockProperties.harvestTool(harvestTool.getInternal());
        return this;
    }
    
    /**
     * Will instruct CoT that this block will not have any loot entries.
     * Currently this will still create a loot table entry, though it will be ignored by the game.
     *
     * @return This builder, used for method chaining
     */
    @ZenCodeType.Method
    public BlockBuilder withoutDrops() {
        blockProperties.noDrops();
        return this;
    }
    
    /**
     * Will instruct CoT to override this block's loot table with the one of the block Provided.
     * Currently this will still create a loot table entry, though it will be ignored by the game.
     *
     * @param blockIn The block whose loot table should be applied
     * @return This builder, used for method chaining
     * @docParam blockIn <block:minecraft:diamond>
     */
    @ZenCodeType.Method
    public BlockBuilder withLootFrom(Block blockIn) {
        blockProperties.lootFrom(blockIn);
        return this;
    }

    /**
     * Sets the block needs tool to harvest.
     *
     * @return This builder, used for method chaining
     */
    @ZenCodeType.Method
    public BlockBuilder setRequiresTool() {
        blockProperties.setRequiresTool();
        return this;
    }

    /**
     * Sets the block can be tinted
     * @return This builder, used for method chaining
     */
    @ZenCodeType.Method
    public BlockBuilder allowTinted() {
        allowTinted = true;
        return this;
    }
    
    /**
     * Sets the specific type of this block.
     * After this method is called the builder's context will switch to the more provided type builder.
     * That means that the methods of this builder will no longer be available, so any properties you wish to set should be set before you call this method.
     *
     * @param typeOfT Internally used by ZC to handle the Generic param
     * @param <T>     The Type of block that this should become
     * @return A builder with the given block.
     * @docParam T mods.contenttweaker.block.pillar.BlockBuilderPillarRotatable
     */
    @ZenCodeType.Method
    public <T extends BlockTypeBuilder> T withType(Class<T> typeOfT) {
        try {
            final Constructor<T> constructor = typeOfT.getConstructor(BlockBuilder.class);
            return constructor.newInstance(this);
        } catch(NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            CraftTweakerAPI.logThrowing("Could not instantiate Specialized Block Builder!", e);
            return null;
        }
    }
    
    @Override
    public void build(ResourceLocation location) {
        withType(BlockBuilderBasic.class).build(location);
    }
    
    
    @Override
    public void build(String resourceLocation) {
        IIsBuilder.super.build(resourceLocation);
    }
}
