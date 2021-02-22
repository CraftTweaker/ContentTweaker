package com.blamejared.contenttweaker.actions;

import com.blamejared.contenttweaker.api.functions.IItemInventoryTick;
import com.blamejared.contenttweaker.items.types.AbstractCoTItem;

public class ActionSetItemInventoryTick extends AbstractActionSetItemFunction<IItemInventoryTick, AbstractCoTItem> {
    public ActionSetItemInventoryTick(IItemInventoryTick function, AbstractCoTItem cotItem) {
        super(function, cotItem);
    }

    @Override
    public void apply() {
        cotItem.itemInventoryTick = function;
    }

    @Override
    public void undo() {
        cotItem.itemInventoryTick = null;
    }

    @Override
    protected String getFunctionField() {
        return "inventoryTick";
    }
}
