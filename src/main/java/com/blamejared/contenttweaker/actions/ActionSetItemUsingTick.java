package com.blamejared.contenttweaker.actions;

import com.blamejared.contenttweaker.api.functions.IItemUsingTick;
import com.blamejared.contenttweaker.items.types.AbstractCoTItem;

public class ActionSetItemUsingTick extends AbstractActionSetItemFunction<IItemUsingTick, AbstractCoTItem> {
    public ActionSetItemUsingTick(IItemUsingTick function, AbstractCoTItem cotItem) {
        super(function, cotItem);
    }

    @Override
    public void apply() {
        cotItem.itemUsingTick = function;
    }

    @Override
    public void undo() {
        cotItem.itemUsingTick = null;
    }

    @Override
    protected String getFunctionField() {
        return "itemUsingTick";
    }
}
