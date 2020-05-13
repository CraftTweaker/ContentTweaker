package com.blamejared.contenttweaker.blocks.types.machine.item.capability;

import com.blamejared.contenttweaker.blocks.types.machine.*;
import com.blamejared.contenttweaker.blocks.types.machine.gui.*;
import com.blamejared.crafttweaker.impl.item.*;
import com.blamejared.crafttweaker.impl.util.*;
import mcp.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

import javax.annotation.*;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemHandlerInventory implements ISidedInventory {
    
    private final CoTItemStackHandler handler;
    private final ItemSlotList slotList;
    
    public ItemHandlerInventory(CoTItemStackHandler handler, ItemSlotList slotList) {
        this.handler = handler;
        this.slotList = slotList;
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
    
    @Override
    public int[] getSlotsForFace(Direction side) {
        return slotList.getSlotsForFace(MCDirection.get(side));
    }
    
    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, @Nullable Direction direction) {
        return slotList.canInsertItem(index, new MCItemStackMutable(itemStackIn), MCDirection.get(direction));
    }
    
    public int getMaxStackSize(int index){
        return slotList.getMaxStackSize(index);
    }
    
    @Override
    public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
        return slotList.canExtractItem(index, new MCItemStackMutable(stack), MCDirection.get(direction));
    }
    
    public void addToContainer(CoTContainer coTContainer) {
        slotList.addToContainer(coTContainer, this);
    }
    
    public boolean tick(CoTTileTicking tile) {
        return slotList.tick(tile, this);
    }
    
    @Nonnull
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        return handler.insertItem(slot, stack, simulate);
    }
    
    @Nonnull
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return handler.extractItem(slot, amount, simulate);
    }
}
