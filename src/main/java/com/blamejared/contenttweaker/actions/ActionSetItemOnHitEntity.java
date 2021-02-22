package com.blamejared.contenttweaker.actions;

import com.blamejared.contenttweaker.api.functions.IItemHitEntity;
import com.blamejared.contenttweaker.items.types.AbstractCoTItem;

public class ActionSetItemOnHitEntity extends AbstractActionSetItemFunction<IItemHitEntity, AbstractCoTItem> {
    public ActionSetItemOnHitEntity(IItemHitEntity function, AbstractCoTItem cotItem) {
        super(function, cotItem);
    }

    @Override
    public void apply() {
        cotItem.itemHitEntity = function;
    }

    @Override
    public void undo() {
        cotItem.itemHitEntity = null;
    }

    @Override
    protected String getFunctionField() {
        return "itemHitEntity";
    }
}
