package com.teamacronymcoders.contenttweaker.api.ctobjects.itemlist;

import crafttweaker.api.item.IItemStack;
import crafttweaker.api.item.WeightedItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.item.MCItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class CTItemList implements ICTItemList {
    private static final Random RANDOM = new Random();
    private final NonNullList<ItemStack> nonNullList;

    public CTItemList(NonNullList<ItemStack> nonNullList) {
        this.nonNullList = nonNullList;
    }

    @Override
    public void add(IItemStack iItemStack) {
        nonNullList.add(CraftTweakerMC.getItemStack(iItemStack));
    }

    @Override
    public void add(WeightedItemStack weightedItemStack) {
        if (RANDOM.nextFloat() < weightedItemStack.getChance()) {
            add(weightedItemStack.getStack());
        }
    }

    @Override
    public void remove(int index) {
        nonNullList.remove(index);
    }

    @Override
    public IItemStack get(int index) {
        return new MCItemStack(nonNullList.get(index));
    }

    @Override
    public IItemStack[] getArray() {
        return nonNullList.parallelStream()
                .map(MCItemStack::new)
                .toArray(IItemStack[]::new);
    }

    @Override
    public List<IItemStack> getList() {
        return nonNullList.parallelStream()
                .map(MCItemStack::new)
                .collect(Collectors.toList());
    }

    @Override
    public int getLength() {
        return nonNullList.size();
    }

    @Override
    public void clear() {
        nonNullList.clear();
    }
}
