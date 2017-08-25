package com.teamacronymcoders.contenttweaker.api.utils;

import crafttweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;

public class CTItemStackUtils {
    public static ItemStack getItemStack(IItemStack stack) {
        ItemStack itemStack = ItemStack.EMPTY;
        if (stack.getInternal() instanceof ItemStack) {
            itemStack = (ItemStack) stack.getInternal();
        }
        return itemStack;
    }
}
