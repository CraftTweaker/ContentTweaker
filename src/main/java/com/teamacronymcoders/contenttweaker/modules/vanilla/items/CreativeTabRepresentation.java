package com.teamacronymcoders.contenttweaker.modules.vanilla.items;

import com.teamacronymcoders.contenttweaker.api.IRepresentation;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.mc1102.item.MCItemStack;
import net.minecraft.item.ItemStack;

public class CreativeTabRepresentation implements IRepresentation, ICreativeTab {
    private String unlocalizedName;
    private ItemStack iconStack;
    private CreativeTabContent creativeTabContent;

    public String getUnlocalizedName() {
        return unlocalizedName;
    }

    public void setUnlocalizedName(String unlocalizedName) {
        this.unlocalizedName = unlocalizedName;
    }

    public IItemStack getIconStack() {
        return new MCItemStack(iconStack);
    }

    public ItemStack getInternalIconStack() {
        return this.iconStack;
    }

    @Override
    public void setIconStack(IItemStack iconStack) {
        if(iconStack.getInternal() instanceof ItemStack) {
            this.setIconStack((ItemStack) iconStack.getInternal());
        } else {
            MineTweakerAPI.logError("Could not get ItemStack");
        }
    }

    public void setIconStack(ItemStack iconStack) {
        this.iconStack = iconStack;
    }

    @Override
    public String getName() {
        return this.getUnlocalizedName();
    }

    @Override
    public String getTypeName() {
        return "Creative Tab";
    }

    @Override
    public void register() {
        if (creativeTabContent == null) {
            creativeTabContent = new CreativeTabContent(this);
        }
    }

    @Override
    public Object getInternal() {
        if (creativeTabContent == null) {
            creativeTabContent = new CreativeTabContent(this);
        }
        return creativeTabContent;
    }
}
