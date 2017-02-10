package com.teamacronymcoders.contenttweaker.modules.vanilla.items;

import net.minecraft.item.ItemStack;

public class CreativeTabRepresentation {
    private String unlocalizedName;
    private ItemStack iconStack;

    public String getUnlocalizedName() {
        return unlocalizedName;
    }

    public void setUnlocalizedName(String unlocalizedName) {
        this.unlocalizedName = unlocalizedName;
    }

    public ItemStack getIconStack() {
        return iconStack;
    }

    public void setIconStack(ItemStack iconStack) {
        this.iconStack = iconStack;
    }
}
