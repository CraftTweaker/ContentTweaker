package com.blamejared.contenttweaker.blocks.types.machine.item.capability;

import com.blamejared.contenttweaker.blocks.types.machine.gui.*;
import com.blamejared.contenttweaker.blocks.types.machine.item.*;
import com.blamejared.contenttweaker.blocks.types.machine.item.builder.*;
import com.blamejared.contenttweaker.blocks.types.machine.item.gui.*;
import com.blamejared.crafttweaker.impl.item.*;
import com.blamejared.crafttweaker.impl.util.*;
import net.minecraft.inventory.*;
import net.minecraftforge.items.*;

import java.util.*;

public class ItemSlot {
    
    private final GroupNames groupNames;
    private final IOSides ioSides;
    private final int index;
    private final ItemSlotGuiInformation guiInformation;
    private final int actualIndex;
    private final ItemSlotItemRestriction itemRestriction;
    
    public ItemSlot(int index, int actualIndex, GroupNames groupNames, IOSides ioSides, ItemSlotGuiInformation guiInformation, ItemSlotItemRestriction itemRestriction) {
        this.groupNames = groupNames;
        this.ioSides = ioSides;
        this.index = index;
        this.guiInformation = guiInformation;
        this.actualIndex = actualIndex;
        this.itemRestriction = itemRestriction;
    }
    
    public void addInputSidesTo(Set<MCDirection> directions) {
        ioSides.addInputSidesTo(directions);
    }
    
    public void addOutputSidesTo(Set<MCDirection> directions) {
        ioSides.addOutputSidesTo(directions);
    }
    
    public int getActualIndex() {
        return actualIndex;
    }
    
    public void addToContainer(CoTContainer container, IInventory handler) {
        if(handler.getSizeInventory() <= actualIndex) {
            final String message = String.format("Inventory too small: Inventory has %d slots, but this slot is at index %d!%nProvided Slot: %s", handler
                    .getSizeInventory(), actualIndex, toString());
            throw new IllegalArgumentException(message);
        }
        
        final CoTSlot slot = new CoTSlot(handler, actualIndex, guiInformation, itemRestriction);
        container.addSlot(slot);
    }
    
    @Override
    public String toString() {
        return "ItemSlot{" + "index=" + index + ", actualIndex=" + actualIndex + '}';
    }
    
    public boolean outputsTo(MCDirection direction) {
        return ioSides.isOutputOnSide(direction);
    }
    
    public boolean inputsFrom(MCDirection direction) {
        return ioSides.isInputOnSide(direction);
    }
    
    public boolean isVisibleFrom(MCDirection direction) {
        return outputsTo(direction) || inputsFrom(direction);
    }
    
    public boolean acceptsItemInput(MCItemStackMutable mcItemStackMutable) {
        return itemRestriction.acceptsItemInput(mcItemStackMutable);
    }
    
    public boolean canOutput(MCItemStackMutable mcItemStackMutable) {
        return itemRestriction.canOutput(mcItemStackMutable);
    }
    
    public boolean needsTicking() {
        return ioSides.needsTicking();
    }
    
    public void fillActiveSides(Set<MCDirection> activeSides) {
        ioSides.fillActiveSides(activeSides);
    }
    
    public boolean tick(ItemHandlerInventory cotInventory, MCDirection direction, IItemHandler itemHandlerOffset){
        return ioSides.tick(cotInventory, actualIndex, direction, itemHandlerOffset, itemRestriction);
    }
    
    public int getMaxStackSize() {
        return itemRestriction.getMaxStackSize();
    }
}
