package com.teamacronymcoders.contenttweaker.modules.vanilla.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class CreativeTabContent extends CreativeTabs {
    private CreativeTabRepresentation representation;

    public CreativeTabContent(CreativeTabRepresentation representation) {
        super(representation.getUnlocalizedName());
        this.representation = representation;
    }

    @Override
    @Nonnull
    @SideOnly(Side.CLIENT)
    public ItemStack getTabIconItem() {
        return representation.getInternalIconStack();
    }
}
