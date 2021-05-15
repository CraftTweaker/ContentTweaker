package com.blamejared.contenttweaker.blocks.types.advanced;

import com.blamejared.contenttweaker.actions.ActionSetFunction;
import com.blamejared.contenttweaker.actions.ActionSetFunctionClient;
import com.blamejared.contenttweaker.api.blocks.IIsCoTBlock;
import com.blamejared.contenttweaker.api.functions.*;
import com.blamejared.contenttweaker.blocks.types.basic.CoTBlockBasic;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Random;

/**
 * A registered CoT Block. Used for advanced functionality. like onRandomTick, onReplaced etc.
 * <p>
 * These functions should be run in CraftTweaker scripts, instead of ContentTweaker ones. And they are reloadable.
 * You can get it via advanced block BEP.
 *
 * @docParam this <advancedblock:test_block>
 */
@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.block.advance.CoTBlockAdvanced")
@Document("mods/contenttweaker/API/block/advance/CoTBlockAdvanced")
public class CoTBlockAdvanced extends CoTBlockBasic implements IIsCoTBlock {
    public CoTBlockAdvanced(Properties properties, Item.Properties itemProperties, ResourceLocation location) {
        super(properties, itemProperties, location);
    }

    private IBlockAdded blockAdded;
    private IBlockNeighborChanged blockNeighborChanged;
    private IBlockRandomTick blockRandomTick;
    private IBlockReplaced blockReplaced;
    private IBlockActivated blockActivated;
    private IBlockColorSupplier blockColorSupplier = IBlockColorSupplier.DEFAULT;

    /**
     * Sets what will happen when the block is added.
     *
     * @return the CoTBlockAdvanced, used for method chaining
     */
    @ZenCodeType.Method
    public CoTBlockAdvanced setOnAdded(IBlockAdded func) {
        ActionSetFunction.applyNewAction("onAdded", this, func, (block, fun) -> block.blockAdded = fun);
        return this;
    }

    /**
     * Sets what will happen when neighbor of the block is changed
     *
     * @return the CoTBlockAdvanced, used for method chaining
     */
    @ZenCodeType.Method
    public CoTBlockAdvanced setOnNeighborChanged(IBlockNeighborChanged func) {
        ActionSetFunction.applyNewAction("onNeighborChanged", this, func, (block, fun) -> block.blockNeighborChanged = fun);
        return this;
    }

    /**
     * Sets what will happen when the block receive a random tick. Throws an exception if the block is not ticks randomly.
     *
     * @return the CoTBlockAdvanced, used for method chaining
     */
    @ZenCodeType.Method
    public CoTBlockAdvanced setOnRandomTick(IBlockRandomTick func) {
        if (!this.getBlock().ticksRandomly(this.getBlock().getDefaultState())) {
            throw new UnsupportedOperationException("You should set the block ticks randomly first! Add `withTickRandomly` to linked block builder.");
        }
        ActionSetFunction.applyNewAction("onRandomTick", this, func, (block, fun) -> block.blockRandomTick = fun);
        return this;
    }

    /**
     * Sets what will happen when the block is replaced. (Note. destroy means replace with air)
     *
     * @return the CoTBlockAdvanced, used for method chaining
     */
    @ZenCodeType.Method
    public CoTBlockAdvanced setOnReplaced(IBlockReplaced func) {
        ActionSetFunction.applyNewAction("onReplaced", this, func, (block, fun) -> block.blockReplaced = fun);
        return this;
    }

    /**
     * Sets what will happen when a player right-clicks the block
     *
     * @param func an IBlockActivated function, the function should return an ActionResultType
     * @return the CoTBlockAdvanced, used for method chaining
     */
    @ZenCodeType.Method
    public CoTBlockAdvanced setOnActivated(IBlockActivated func) {
        ActionSetFunction.applyNewAction("onActivated", this, func, (block, fun) -> block.blockActivated = fun);
        return this;
    }

    /**
     * The block's color
     * @param func an IBlockColorSupplier. The tintIndex argument is a hardcode of its model
     * @return the CoTBlockAdvanced, used for method chaining.
     */
    @ZenCodeType.Method
    public CoTBlockAdvanced setBlockColorSupplier(IBlockColorSupplier func) {
        ActionSetFunctionClient.applyNewAction("blockColorSupplier", this, func, IBlockColorSupplier.DEFAULT, (block, fun) -> block.blockColorSupplier = func);
        return this;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (blockAdded != null) {
            blockAdded.apply(state, worldIn, pos, oldState, isMoving);
        } else {
            super.onBlockAdded(state, worldIn, pos, oldState, isMoving);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (blockActivated != null) {
            return blockActivated.apply(state, worldIn, pos, player, handIn);
        } else {
            return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (blockNeighborChanged != null) {
            blockNeighborChanged.apply(state, worldIn, pos, blockIn, fromPos, isMoving);
        } else {
            super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (blockRandomTick != null) {
            blockRandomTick.apply(state, worldIn, pos, random);
        } else {
            super.randomTick(state, worldIn, pos, random);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (blockReplaced != null) {
            blockReplaced.apply(state, worldIn, pos, newState, isMoving);
        } else {
            super.onReplaced(state, worldIn, pos, newState, isMoving);
        }
    }

    public int getColor(BlockState state, IBlockDisplayReader world, BlockPos pos, int tintIndex) {
        return blockColorSupplier.apply(state, world, pos, tintIndex);
    }
}
