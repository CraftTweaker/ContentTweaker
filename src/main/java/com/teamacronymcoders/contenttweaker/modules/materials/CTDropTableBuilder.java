package com.teamacronymcoders.contenttweaker.modules.materials;

import com.teamacronymcoders.base.items.DropTableBuilder;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.OperatorType;
import stanhebben.zenscript.annotations.ZenCaster;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenOperator;

@ZenClass("mods.contenttweaker.DropTableBuilder")
@ZenRegister
public class CTDropTableBuilder {
    private DropTableBuilder builder;
    
    public CTDropTableBuilder() {
        builder = new DropTableBuilder();
    }
    
    public CTDropTableBuilder(String oldSlots) {
        builder = new DropTableBuilder(oldSlots);
    }
    
    @ZenMethod
    public static CTDropTableBuilder newSlot() {
        return new CTDropTableBuilder();
    }
    
    @ZenMethod
    public CTDropTableBuilder addItem(String item) {
        builder.addItem(item);
        return this;
    }
    
    @ZenMethod
    public CTDropTableBuilder addItem(String item, int weight) {
        builder.addItem(item, weight);
        return this;
    }
    
    @ZenMethod
    public CTDropTableBuilder addItem(String item, int weight, int count) {
        builder.addItem(item, weight, count);
        return this;
    }
    
    @ZenMethod
    public CTDropTableBuilder enableFortune() {
        builder.enableFortune();
        return this;
    }
    
    @ZenOperator(OperatorType.ADD)
    public String combineSlots(CTDropTableBuilder slot) {
        return builder.toString() + slot.toString();
    }
    
    @ZenCaster
    public String toString() {
        return builder.toString();
    }
}
