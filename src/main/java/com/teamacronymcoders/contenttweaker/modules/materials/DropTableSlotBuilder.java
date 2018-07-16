package com.teamacronymcoders.contenttweaker.modules.materials;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.OperatorType;
import stanhebben.zenscript.annotations.ZenCaster;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenOperator;

import java.util.ArrayList;
import java.util.List;

@ZenClass("mods.contenttweaker.DropTableSlotBuilder")
@ZenRegister
public class DropTableSlotBuilder {
    private List<String> items = new ArrayList<String>();
    private boolean fortuneEnabled = false;
    private String baseString = "";
    
    public DropTableSlotBuilder() {
    }
    
    public DropTableSlotBuilder(String baseString) {
        this.baseString = baseString;
    }
    
    @ZenMethod
    public static DropTableSlotBuilder newSlot() {
        return new DropTableSlotBuilder();
    }
    
    @ZenMethod
    public DropTableSlotBuilder addItem(String item) {
        items.add(item);
        return this;
    }
    
    @ZenMethod
    public DropTableSlotBuilder addItem(String item, int weight) {
        items.add(item + "%" + weight);
        return this;
    }
    
    @ZenMethod
    public DropTableSlotBuilder addItem(String item, int weight, int count) {
        items.add(item + "%" + weight + "%" + count);
        return this;
    }
    
    @ZenMethod
    public DropTableSlotBuilder enableFortune() {
        fortuneEnabled = true;
        return this;
    }
    
    @ZenOperator(OperatorType.ADD)
    public String combineSlots(DropTableSlotBuilder slot) {
        return this.toString() + slot.toString();
    }
    
    @ZenCaster
    public String toString() {
        StringBuilder sb = new StringBuilder(baseString);
        sb.append("[");
        sb.append(items.get(0));

        for (int i = 1; i < items.size(); i++) {
            sb.append("#").append(items.get(i));
        }
        if (fortuneEnabled) {
            sb.append("$true");
        }
        return sb.append("]").toString();
    }
}