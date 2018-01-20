package com.teamacronymcoders.contenttweaker.modules.vanilla.items;

import com.teamacronymcoders.contenttweaker.api.IRepresentation;
import com.teamacronymcoders.contenttweaker.modules.vanilla.functions.ISupplyItemStack;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import java.util.Optional;

public class CreativeTabRepresentation implements IRepresentation<CreativeTabs>, ICreativeTab {
    private String unlocalizedName;
    private ISupplyItemStack supplyItemStack;
    private CreativeTabContent creativeTabContent;

    public String getUnlocalizedName() {
        return unlocalizedName;
    }

    public void setUnlocalizedName(String unlocalizedName) {
        this.unlocalizedName = unlocalizedName;
    }

    public IItemStack getIconStack() {
        return getSupplyItemStack().get();
    }

    public ItemStack getInternalIconStack() {
        return Optional.ofNullable(getSupplyItemStack().get())
                        .map(IIngredient::getInternal)
                        .map(iItemStack -> (ItemStack) iItemStack)
                        .orElse(ItemStack.EMPTY);
    }

    @Override
    public void setIconStack(IItemStack iconStack) {
        this.supplyItemStack = () -> iconStack;
    }

    @Override
    public ISupplyItemStack getIconStackSupplier() {
        return this.supplyItemStack;
    }

    @Override
    public void setIconStackSupplier(ISupplyItemStack stackSupplier) {
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

    public ISupplyItemStack getSupplyItemStack() {
        return supplyItemStack;
    }

    public void setSupplyItemStack(ISupplyItemStack supplyItemStack) {
        this.supplyItemStack = supplyItemStack;
    }
}
