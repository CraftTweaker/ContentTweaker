package com.teamacronymcoders.contenttweaker.api.ctobjects.itemlist;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.item.WeightedItemStack;
import stanhebben.zenscript.annotations.OperatorType;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenOperator;

import java.util.List;

@ZenRegister
@ZenClass("mods.contenttweaker.ItemList")
public interface ICTItemList {
    @ZenMethod
    @ZenOperator(OperatorType.ADD)
    void add(IItemStack iItemStack);

    @ZenMethod
    @ZenOperator(OperatorType.ADD)
    void add(WeightedItemStack weightedItemStack);

    @ZenMethod
    void remove(int index);

    @ZenMethod
    IItemStack get(int index);

    @ZenMethod
    IItemStack[] getArray();

    @ZenMethod
    List<IItemStack> getList();

    @ZenMethod
    int getLength();

    @ZenMethod
    void clear();
}
