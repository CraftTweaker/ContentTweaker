package com.teamacronymcoders.contenttweaker.modules.vanilla.items;

import com.teamacronymcoders.contenttweaker.api.IRepresentation;
import com.teamacronymcoders.contenttweaker.modules.vanilla.functions.IItemStackSupplier;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import java.util.Optional;

public class CreativeTabRepresentation implements IRepresentation<CreativeTabs>, ICreativeTab {
    private String unlocalizedName;
    private IItemStackSupplier supplyItemStack;
    private CreativeTabContent creativeTabContent;

    public String getUnlocalizedName() {
        return unlocalizedName;
    }

    public void setUnlocalizedName(String unlocalizedName) {
        this.unlocalizedName = unlocalizedName;
    }

    public IItemStack getIconStack() {
        return getSupplyItemStack().getItemStack();
    }

    public ItemStack getInternalIconStack() {
        return Optional.ofNullable(getSupplyItemStack().getItemStack())
                        .map(IIngredient::getInternal)
                        .map(iItemStack -> (ItemStack) iItemStack)
                        .orElse(ItemStack.EMPTY);
    }

    @Override
    public void setIconStack(IItemStack iconStack) {
        this.supplyItemStack = () -> iconStack;
    }

    @Override
    public IItemStackSupplier getIconStackSupplier() {
        return this.supplyItemStack;
    }

    @Override
    public void setIconStackSupplier(IItemStackSupplier stackSupplier) {
        this.supplyItemStack = stackSupplier;
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
    public CreativeTabs getInternal() {
        if (creativeTabContent == null) {
            creativeTabContent = new CreativeTabContent(this);
        }
        return creativeTabContent;
    }

    public IItemStackSupplier getSupplyItemStack() {
        return supplyItemStack;
    }

    public void setSupplyItemStack(IItemStackSupplier supplyItemStack) {
        this.supplyItemStack = supplyItemStack;
    }
}
