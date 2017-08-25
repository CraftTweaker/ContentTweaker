package com.teamacronymcoders.contenttweaker.api.ctobjects.inventory;

import crafttweaker.api.item.IItemStack;

public interface ICTInventory {
    int getSlots();

    IItemStack getStackInSlot(int slot);

    IItemStack insertItem(int slot, IItemStack stack, boolean simulate);

    IItemStack extractItem(int slot, int amount, boolean simulate);

    int getSlotLimit(int slot);

    void setStackInSlot(int slot, IItemStack stack);
}
