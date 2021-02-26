package com.blamejared.contenttweaker.actions;

import com.blamejared.contenttweaker.api.blocks.IIsCoTBlock;
import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import net.minecraftforge.fml.LogicalSide;

public abstract class AbstractActionSetBlockFunction<T, B extends IIsCoTBlock> implements IUndoableAction {
    protected final T function;
    protected final B cotBlock;

    public AbstractActionSetBlockFunction(T function, B cotBlock) {
        this.function = function;
        this.cotBlock = cotBlock;
    }

    protected abstract String getFunctionField();

    @Override
    public boolean shouldApplyOn(LogicalSide side) {
        return true;
    }

    @Override
    public String describe() {
        return "Setting " + getFunctionField() + " of Block: " + cotBlock.getRegistryName().toString();
    }

    @Override
    public String describeUndo() {
        return "Undoing " + getFunctionField() + " of Block: " + cotBlock.getRegistryName().toString();
    }
}
