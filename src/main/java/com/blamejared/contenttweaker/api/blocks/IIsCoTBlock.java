package com.blamejared.contenttweaker.api.blocks;

import com.blamejared.contenttweaker.actions.ActionSetFunction;
import com.blamejared.contenttweaker.api.IHasCoTItem;
import com.blamejared.contenttweaker.api.IHasResourceLocation;
import com.blamejared.contenttweaker.api.IHasResourcesToWrite;
import com.blamejared.contenttweaker.api.functions.*;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraftforge.common.extensions.IForgeBlock;
import org.openzen.zencode.java.ZenCodeType;

/**
 * A registered CoT Block. Used for advanced functionality. like onRandomTick, onReplaced etc.
 * <p>
 * These functions should be run in CraftTweaker scripts, instead of ContentTweaker ones. And they are reloadable.
 * You can get it via cotItem BEP.
 */
@ZenRegister
@Document("mods/contenttweaker/API/block/IIsCotBlock")
@ZenCodeType.Name("mods.contenttweaker.block.IIsCotBlock")
public interface IIsCoTBlock extends IHasCoTItem, IHasResourcesToWrite, IHasResourceLocation, IForgeBlock {
    /**
     * Sets what will happen when the block is added.
     *
     * @return the IIsCoTBlock, used for method chaining
     */
    default IIsCoTBlock setOnAdded(IBlockAdded func) {
        ActionSetFunction.applyNewAction(func, IBlockAdded.class, this);
        return this;
    }

    /**
     * Sets what will happen when neighbor of the block is changed
     *
     * @return the IIsCoTBlock, used for method chaining
     */
    default IIsCoTBlock setOnNeighborChanged(IBlockNeighborChanged func) {
        ActionSetFunction.applyNewAction(func, IBlockNeighborChanged.class, this);
        return this;
    }

    /**
     * Sets what will happen when the block receive a random tick. Throws an exception if the block is not ticks randomly.
     *
     * @return the IIsCoTBlock, used for method chaining
     */
    default IIsCoTBlock setOnRandomTick(IBlockRandomTick func) {
        if (!this.getBlock().ticksRandomly(this.getBlock().getDefaultState())) {
            throw new UnsupportedOperationException("You should set the block ticks randomly first! Add `withTickRandomly` to linked block builder.");
        }
        ActionSetFunction.applyNewAction(func, IBlockRandomTick.class, this);
        return this;
    }

    /**
     * Sets what will happen when the block is replaced. (Note. destroy means replace with air)
     *
     * @return the IIsCoTBlock, used for method chaining
     */
    default IIsCoTBlock setOnReplaced(IBlockReplaced func) {
        ActionSetFunction.applyNewAction(func, IBlockReplaced.class, this);
        return this;
    }

    /**
     * Sets what will happen when a player right-clicks the block
     *
     * @param func an IBlockActivated function, the function should return a string representing action result
     *             ("SUCCESS", "PASS", "FAIL", "CONSUME")
     * @return the IIsCoTBlock, used for method chaining
     */
    default IIsCoTBlock setOnActivated(IBlockActivated func) {
        ActionSetFunction.applyNewAction(func, IBlockActivated.class, this);
        return this;
    }
}
