package com.blamejared.contenttweaker.blocks.types.machine.item.capability;

import mcp.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;

import javax.annotation.*;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemHandlerInventory implements IInventory {
    
    private final CoTItemStackHandler handler;
    
    public ItemHandlerInventory(CoTItemStackHandler handler) {
        this.handler = handler;
    }
    
    @Override
    public int getSizeInventory() {
        return handler.getSlots();
    }
    
    @Override
    public boolean isEmpty() {
        for(ItemStack stack : handler.getStacks()) {
            if(!stack.isEmpty()) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public ItemStack getStackInSlot(int index) {
        return handler.getStackInSlot(index);
    }
    
    @Override
    public ItemStack decrStackSize(int index, int count) {
        return handler.getStackInSlot(index).split(count);
    }
    
    @Override
    public ItemStack removeStackFromSlot(int index) {
        final ItemStack stackInSlot = handler.getStackInSlot(index);
        handler.setStackInSlot(index, ItemStack.EMPTY);
        return stackInSlot;
    }
    
    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        handler.setStackInSlot(index, stack);
    }
    
    @Override
    public void markDirty() {
        handler.markDirty();
    }
    
    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        return true;
    }
    
    @Override
    public void clear() {
        handler.clear();
    }
}
