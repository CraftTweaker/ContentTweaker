package com.blamejared.contenttweaker.blocks.types.machine.item.builder;

import com.blamejared.contenttweaker.blocks.types.machine.item.*;
import com.blamejared.contenttweaker.blocks.types.machine.item.capability.*;
import com.blamejared.contenttweaker.blocks.types.machine.item.gui.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.impl.util.*;
import org.openzen.zencode.java.*;

@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.block.machine.ItemCapabilitySlotBuilder")
public class ItemCapabilitySlotBuilder {
    
    private final int index;
    private final GroupNames groupNames;
    private final IOSides ioSides;
    private final ItemSlotGuiInformation guiInformation;
    
    public ItemCapabilitySlotBuilder(int index) {
        this.index = index;
        this.groupNames = new GroupNames();
        this.ioSides = new IOSides();
        this.guiInformation = new ItemSlotGuiInformation();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("index")
    public int getIndex() {
        return index;
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
    public ItemCapabilitySlotBuilder withColor(int color) {
        guiInformation.setColor(color);
        return this;
    }
    
    @ZenCodeType.Method
    public boolean isInGroup(String name) {
        return groupNames.hasName(name);
    }
    
    public ItemSlot buildItemSlot() {
        return new ItemSlot(index, groupNames, ioSides, guiInformation);
    }
}
