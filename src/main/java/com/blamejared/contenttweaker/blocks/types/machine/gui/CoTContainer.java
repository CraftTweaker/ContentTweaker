package com.blamejared.contenttweaker.blocks.types.machine.gui;

import com.blamejared.contenttweaker.blocks.types.machine.item.capability.*;
import mcp.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.container.*;
import net.minecraft.item.*;

import javax.annotation.*;
import java.util.*;
import java.util.stream.*;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CoTContainer extends Container {
    
    
    public CoTContainer(ContainerType<CoTContainer> type, int id) {
        super(type, id);
    }
    
    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }
    
    @Override
    protected Slot addSlot(Slot slotIn) {
        return super.addSlot(slotIn);
    }
    
    public void addSlot(CoTSlot slotIn) {
        addSlot((Slot) slotIn);
    }
    
    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        final Slot slot = getSlot(index);
        if(slot.inventory == playerIn.inventory) {
            //Click was on Player slot
            return doTheInsertionThing(getNonPlayerSlots(playerIn.inventory), slot);
        } else {
            //Click was on Machine slot
            return doTheInsertionThing(getPlayerSlots(playerIn.inventory), slot);
        }
    }
    
    private List<Slot> getPlayerSlots(PlayerInventory playerInventory) {
        List<Slot> list = new ArrayList<>();
        for(Slot s : this.inventorySlots) {
            if(s.inventory == playerInventory) {
                list.add(s);
            }
        }
        return list;
    }
    
    private List<Slot> getNonPlayerSlots(PlayerInventory playerInventory){
        List<Slot> list = new ArrayList<>();
        for(Slot s : this.inventorySlots) {
            if(s.inventory != playerInventory) {
                list.add(s);
            }
        }
        return list;
    }
    
    @Override
    public boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection) {
        return super.mergeItemStack(stack, startIndex, endIndex, reverseDirection);
    }
    
    private ItemStack doTheInsertionThing(List<Slot> slots, Slot theClickedSlot) {
        if(!theClickedSlot.getHasStack()) {
            return ItemStack.EMPTY;
        }
    
        ItemStack clickedSlack = theClickedSlot.getStack();
    
        for(Slot slot : slots) {
            final int slotIndex = slot.slotNumber;
            if(slot instanceof CoTSlot && !((CoTSlot)slot).canAccept(clickedSlack)) {
                continue;
            }
            if(mergeItemStack(clickedSlack, slotIndex, slotIndex + 1, false)) {
                return clickedSlack;
            }
        }
        return ItemStack.EMPTY;
    }
}
