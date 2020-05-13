package com.blamejared.contenttweaker.blocks.types.machine.item.builder;

import com.blamejared.contenttweaker.blocks.types.machine.item.capability.*;
import com.blamejared.crafttweaker.impl.item.*;
import com.blamejared.crafttweaker.impl.util.*;
import net.minecraft.item.*;
import net.minecraftforge.items.*;

import java.util.*;

public class IOSides {
    
    private final Set<MCDirection> inputSides;
    private final Set<MCDirection> outputSides;
    private final Set<MCDirection> pullInputSides;
    private final Set<MCDirection> pushOutputs;
    
    IOSides() {
        this.inputSides = new HashSet<>();
        this.pullInputSides = new HashSet<>();
        this.outputSides = new HashSet<>();
        this.pushOutputs = new HashSet<>();
    }
    
    void setInputOnSide(MCDirection side) {
        inputSides.add(side);
    }
    
    public boolean isInputOnSide(MCDirection direction) {
        return inputSides.contains(direction);
    }
    
    void setPullFromSide(MCDirection side) {
        setInputOnSide(side);
        pullInputSides.add(side);
    }
    
    public boolean doesPullFromSide(MCDirection direction) {
        return pullInputSides.contains(direction);
    }
    
    
    void setOutputOnSide(MCDirection side) {
        outputSides.add(side);
    }
    
    public boolean isOutputOnSide(MCDirection side) {
        return outputSides.contains(side);
    }
    
    void setPushToSide(MCDirection side) {
        setOutputOnSide(side);
        pushOutputs.add(side);
    }
    
    public boolean doesPushToSide(MCDirection side) {
        return pushOutputs.contains(side);
    }
    
    public void addInputSidesTo(Set<MCDirection> directions) {
        directions.addAll(inputSides);
    }
    
    public void addOutputSidesTo(Set<MCDirection> directions) {
        directions.addAll(inputSides);
    }
    
    public boolean needsTicking() {
        return !pullInputSides.isEmpty() || !pushOutputs.isEmpty();
    }
    
    public void fillActiveSides(Set<MCDirection> activeSides) {
        activeSides.addAll(pullInputSides);
        activeSides.addAll(pushOutputs);
    }
    
    public boolean tick(ItemHandlerInventory thisHandler, int actualIndex, MCDirection direction, IItemHandler itemHandlerOffset, ItemSlotItemRestriction itemRestriction) {
        ItemStack stackInSlot = thisHandler.getStackInSlot(actualIndex);
        final int slots = itemHandlerOffset.getSlots();
        final int countBefore = stackInSlot.getCount();
        boolean dirty = false;
        
        
        if(doesPushToSide(direction)) {
            for(int i = 0; i < slots; i++) {
                if(!itemRestriction.canOutput(new MCItemStackMutable(stackInSlot))){
                    continue;
                }
                stackInSlot = itemHandlerOffset.insertItem(i, stackInSlot, false);
                if(stackInSlot.getCount() < countBefore) {
                    thisHandler.setInventorySlotContents(actualIndex, stackInSlot);
                    dirty = true;
                    break;
                }
            }
        }
        
        if(doesPullFromSide(direction)) {
            for(int i = 0; i < slots; i++) {
                ItemStack itemStack = itemHandlerOffset.getStackInSlot(i).copy();
                if(itemStack.isEmpty()) {
                    continue;
                }
                if(!itemRestriction.acceptsItemInput(new MCItemStackMutable(itemStack))){
                    continue;
                }
                final ItemStack inserted = thisHandler.insertItem(actualIndex, itemStack, false);
                if(inserted.equals(itemStack, false)) {
                    continue;
                }
                itemHandlerOffset.extractItem(i, itemStack.getCount() - inserted.getCount(), false);
                dirty = true;
                break;
            }
        }
        
        return dirty;
    }
}
