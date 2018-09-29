package com.teamacronymcoders.contenttweaker.api.ctobjects.capabilities.item;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.item.MCItemStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.contenttweaker.ItemCapability")
public class ItemCapability {
    private final IItemHandler itemHandler;

    public ItemCapability(IItemHandler itemHandler) {
        this.itemHandler = itemHandler;
    }

    @ZenMethod
    public int getNumberOfSlots() {
        return itemHandler.getSlots();
    }

    @ZenMethod
    public IItemStack getStackInSlot(int slot) {
        return returnStack(this.itemHandler.getStackInSlot(slot));
    }

    @ZenMethod
    public IItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        return returnStack(this.itemHandler.insertItem(slot, stack, simulate));
    }

    @ZenMethod
    public IItemStack insertItem(IItemStack itemStackInput, boolean simulate) {
        return returnStack(ItemHandlerHelper.insertItem(itemHandler, CraftTweakerMC.getItemStack(itemStackInput), simulate));
    }

    @ZenMethod
    public IItemStack extractItem(int slot, int amount, boolean simulate) {
        return returnStack(this.itemHandler.extractItem(slot, amount, simulate));
    }

    private IItemStack returnStack(ItemStack itemStack) {
        return itemStack.isEmpty() ? null : new MCItemStack(itemStack);
    }
}
