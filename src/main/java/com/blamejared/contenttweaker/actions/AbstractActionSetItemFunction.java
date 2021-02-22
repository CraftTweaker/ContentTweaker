package com.blamejared.contenttweaker.actions;

import com.blamejared.contenttweaker.api.items.IIsCotItem;
import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import net.minecraftforge.fml.LogicalSide;

/**
 * @author youyihj
 */
public abstract class AbstractActionSetItemFunction<T, I extends IIsCotItem> implements IUndoableAction {
    protected final T function;
    protected final I cotItem;

    public AbstractActionSetItemFunction(T function, I cotItem) {
        this.function = function;
        this.cotItem = cotItem;
    }

    protected abstract String getFunctionField();

    @Override
    public boolean shouldApplyOn(LogicalSide side) {
        return true;
    }

    @Override
    public String describe() {
        return "Setting " + getFunctionField() + " of Item: " + cotItem.getRegistryName().toString();
    }

    @Override
    public String describeUndo() {
        return "Undoing " + getFunctionField() + " of Item: " + cotItem.getRegistryName().toString();
    }
}
