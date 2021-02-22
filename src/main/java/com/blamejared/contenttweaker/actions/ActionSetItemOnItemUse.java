package com.blamejared.contenttweaker.actions;

import com.blamejared.contenttweaker.api.functions.IItemUse;
import com.blamejared.contenttweaker.items.types.AbstractCoTItem;

public class ActionSetItemOnItemUse extends AbstractActionSetItemFunction<IItemUse, AbstractCoTItem> {
    public ActionSetItemOnItemUse(IItemUse function, AbstractCoTItem cotItem) {
        super(function, cotItem);
    }

    @Override
    public void apply() {
        cotItem.itemUse = function;
    }

    @Override
    public void undo() {
        cotItem.itemUse = null;
    }

    @Override
    protected String getFunctionField() {
        return "onItemUse";
    }
}
