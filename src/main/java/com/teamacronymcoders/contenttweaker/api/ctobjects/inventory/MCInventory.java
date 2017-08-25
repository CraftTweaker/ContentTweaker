package com.teamacronymcoders.contenttweaker.api.ctobjects.inventory;

import com.teamacronymcoders.contenttweaker.api.utils.CTItemStackUtils;
import crafttweaker.api.item.IItemStack;
import crafttweaker.mc1120.item.MCItemStack;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.InvWrapper;

public class MCInventory implements ICTInventory {
    private IItemHandlerModifiable inventory;

    public MCInventory(IInventory inventory) {
        this.inventory = new InvWrapper(inventory);
    }

    @Override
    public int getSlots() {
        return inventory.getSlots();
    }

    @Override
    public IItemStack getStackInSlot(int slot) {
        return new MCItemStack(inventory.getStackInSlot(slot));
    }

    @Override
    public IItemStack insertItem(int slot, IItemStack stack, boolean simulate) {
        return new MCItemStack(inventory.insertItem(slot, CTItemStackUtils.getItemStack(stack), simulate));
    }

    @Override
    public IItemStack extractItem(int slot, int amount, boolean simulate) {
        return new MCItemStack(inventory.extractItem(slot, amount, simulate));
    }

    @Override
    public int getSlotLimit(int slot) {
        return inventory.getSlotLimit(slot);
    }

    @Override
    public void setStackInSlot(int slot, IItemStack stack) {
        inventory.setStackInSlot(slot, CTItemStackUtils.getItemStack(stack));
    }
}
