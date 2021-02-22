package com.blamejared.contenttweaker.actions;

import com.blamejared.contenttweaker.api.functions.IItemInteractWithEntity;
import com.blamejared.contenttweaker.items.types.AbstractCoTItem;

public class ActionSetItemOnInteractWithEntity extends AbstractActionSetItemFunction<IItemInteractWithEntity, AbstractCoTItem> {
    public ActionSetItemOnInteractWithEntity(IItemInteractWithEntity function, AbstractCoTItem cotItem) {
        super(function, cotItem);
    }

    @Override
    public void apply() {
        cotItem.itemInteractWithEntity = function;
    }

    @Override
    public void undo() {
        cotItem.itemInteractWithEntity = null;
    }

    @Override
    protected String getFunctionField() {
        return "itemInteractWithEntity";
    }
}
