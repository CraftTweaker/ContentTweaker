package com.blamejared.contenttweaker.actions;

import com.blamejared.contenttweaker.api.functions.IItemInventoryTick;
import com.blamejared.contenttweaker.blocks.CoTBlockItem;

public class ActionSetBlockItemInventoryTick extends AbstractActionSetItemFunction<IItemInventoryTick, CoTBlockItem> {
    public ActionSetBlockItemInventoryTick(IItemInventoryTick function, CoTBlockItem cotItem) {
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
