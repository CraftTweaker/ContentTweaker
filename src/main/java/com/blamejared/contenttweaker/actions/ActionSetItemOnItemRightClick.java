package com.blamejared.contenttweaker.actions;

import com.blamejared.contenttweaker.api.functions.IItemRightClick;
import com.blamejared.contenttweaker.items.types.AbstractCoTItem;

public class ActionSetItemOnItemRightClick extends AbstractActionSetItemFunction<IItemRightClick, AbstractCoTItem> {
    public ActionSetItemOnItemRightClick(IItemRightClick function, AbstractCoTItem cotItem) {
        super(function, cotItem);
    }

    @Override
    public void apply() {
        cotItem.itemRightClick = function;
    }

    @Override
    public void undo() {
        cotItem.itemRightClick = null;
    }

    @Override
    protected String getFunctionField() {
        return "onItemRightClick";
    }
}
