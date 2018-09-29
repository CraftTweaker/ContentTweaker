package com.teamacronymcoders.contenttweaker.modules.vanilla.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Optional;

public class CreativeTabContent extends CreativeTabs {
    private CreativeTabRepresentation representation;
    private ItemStack iconStack;

    public CreativeTabContent(CreativeTabRepresentation representation) {
        super(representation.getUnlocalizedName());
        this.representation = representation;
        setFields();
    }

    private void setFields() {
        this.setBackgroundImageName(representation.getBackGroundImage());
        if (representation.isNoScrollBar()) {
            this.setNoScrollbar();
        }
        if (representation.isNoTitle()) {
            this.setNoTitle();
        }
    }

    @Override
    public boolean hasSearchBar() {
        return this.representation.isHasSearch();
    }

    @Override
    @Nonnull
    @SideOnly(Side.CLIENT)
    public ItemStack createIcon() {
        return iconStack = Optional.ofNullable(iconStack)
                .orElseGet(() -> representation.getInternalIconStack());
    }
}
