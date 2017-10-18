package com.teamacronymcoders.contenttweaker.api.ctobjects.mutableitemstack;

import com.teamacronymcoders.contenttweaker.api.ctobjects.entity.player.ICTPlayer;
import crafttweaker.mc1120.item.MCItemStack;
import net.minecraft.item.ItemStack;

public class MCMutableItemStack extends MCItemStack implements IMutableItemStack {
    private ItemStack itemStack;

    public MCMutableItemStack(ItemStack itemStack) {
        super(itemStack);
        this.itemStack = itemStack;
    }

    @Override
    public void setCount(int count) {
        this.itemStack.setCount(count);
    }

    @Override
    public void shrink(int count) {
        this.itemStack.shrink(count);
    }

    @Override
    public void grow(int count) {
        this.itemStack.grow(count);
    }

    @Override
    public void damage(int amount, ICTPlayer entity) {
        this.itemStack.damageItem(amount, entity.getInternal());
    }
}
