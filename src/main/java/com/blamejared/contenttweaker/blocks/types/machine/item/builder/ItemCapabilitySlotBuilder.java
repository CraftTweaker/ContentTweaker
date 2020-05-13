package com.blamejared.contenttweaker.blocks.types.machine.item.builder;

import com.blamejared.contenttweaker.blocks.types.machine.item.*;
import com.blamejared.contenttweaker.blocks.types.machine.item.capability.*;
import com.blamejared.contenttweaker.blocks.types.machine.item.gui.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.api.item.*;
import com.blamejared.crafttweaker.impl.item.*;
import com.blamejared.crafttweaker.impl.util.*;
import org.openzen.zencode.java.*;

import java.util.function.*;

@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.block.machine.ItemCapabilitySlotBuilder")
public class ItemCapabilitySlotBuilder {
    
    private final int specifiedIndex;
    private final GroupNames groupNames;
    private final IOSides ioSides;
    private final ItemSlotGuiInformation guiInformation;
    private final ItemSlotItemRestriction itemRestriction;
    
    public ItemCapabilitySlotBuilder(int specifiedIndex) {
        this.specifiedIndex = specifiedIndex;
        this.groupNames = new GroupNames();
        this.ioSides = new IOSides();
        this.guiInformation = new ItemSlotGuiInformation();
        itemRestriction = new ItemSlotItemRestriction();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("index")
    public int getSpecifiedIndex() {
        return specifiedIndex;
    }
    
    @ZenCodeType.Method
    public ItemCapabilitySlotBuilder addToGroup(String groupName) {
        groupNames.addName(groupName);
        return this;
    }
    
    @ZenCodeType.Method
    public ItemCapabilitySlotBuilder setInputOn(MCDirection facing) {
        ioSides.setInputOnSide(facing);
        return this;
    }
    
    @ZenCodeType.Method
    public ItemCapabilitySlotBuilder setOutputOn(MCDirection side) {
        ioSides.setOutputOnSide(side);
        return this;
    }
    
    @ZenCodeType.Method
    public ItemCapabilitySlotBuilder setPushTo(MCDirection side) {
        ioSides.setPushToSide(side);
        return this;
    }
    
    @ZenCodeType.Method
    public ItemCapabilitySlotBuilder setPullFrom(MCDirection side) {
        ioSides.setPullFromSide(side);
        return this;
    }
    
    @ZenCodeType.Method
    public ItemCapabilitySlotBuilder setHidden() {
        guiInformation.hide();
        return this;
    }
    
    @ZenCodeType.Method
    public ItemCapabilitySlotBuilder withGuiX(int x) {
        guiInformation.setPositionX(x);
        return this;
    }
    
    @ZenCodeType.Method
    public ItemCapabilitySlotBuilder withGuiY(int y) {
        guiInformation.setPositionY(y);
        return this;
    }
    
    @ZenCodeType.Method
    public ItemCapabilitySlotBuilder withGuiPosition(int x, int y) {
        guiInformation.setPositionX(x);
        guiInformation.setPositionY(y);
        return this;
    }
    
    @ZenCodeType.Method
    public ItemCapabilitySlotBuilder withMaxStackSize(int stackSize) {
        this.itemRestriction.setMaxStackSize(stackSize);
        return this;
    }
    
    @ZenCodeType.Method
    public ItemCapabilitySlotBuilder withInputRestriction(Predicate<IItemStack> predicate) {
        this.itemRestriction.setCanInputPredicate(predicate);
        return this;
    }
    
    @ZenCodeType.Method
    public ItemCapabilitySlotBuilder withOutputRestriction(Predicate<IItemStack> predicate) {
        this.itemRestriction.setCanOutputPredicate(predicate);
        return this;
    }
    
    @ZenCodeType.Method
    public boolean isInGroup(String name) {
        return groupNames.hasName(name);
    }
    
    public ItemSlot buildItemSlot(int actualIndex) {
        return new ItemSlot(specifiedIndex, actualIndex, groupNames, ioSides, guiInformation, itemRestriction);
    }
}
