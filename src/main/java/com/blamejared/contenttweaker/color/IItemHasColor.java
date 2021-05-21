package com.blamejared.contenttweaker.color;

import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;

/**
 * @author youyihj
 */
public interface IItemHasColor extends IItemProvider {
    int getColor(ItemStack stack, int tintIndex);
}
