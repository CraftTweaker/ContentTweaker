package com.teamacronymcoders.contenttweaker.modules.vanilla.items;

import com.teamacronymcoders.contenttweaker.api.MissingFieldsException;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class ItemContent extends Item {
    private ItemRepresentation itemRepresentation;
    private CreativeTabs creativeTab;
    public ItemContent(ItemRepresentation itemRepresentation) {
        checkFields(itemRepresentation);
        setFields(itemRepresentation);
    }

    /* Beginning of Representation stuff */
    public void checkFields(ItemRepresentation itemRepresentation) {
        List<String> missingFields = new ArrayList<>();
        if(itemRepresentation.getUnlocalizedName() == null) {
            missingFields.add("unlocalizedName");
        }
        if(!missingFields.isEmpty()) {
            throw new MissingFieldsException("Block", missingFields);
        }
    }

    public void setFields(ItemRepresentation itemRepresentation) {
        this.setUnlocalizedName(itemRepresentation.getUnlocalizedName());
        this.setCreativeTab(itemRepresentation.getCreativeTab());
        this.setMaxStackSize(itemRepresentation.getMaxStackSize());
        this.setHarvestLevel(itemRepresentation.getToolClass(), itemRepresentation.getToolLevel());
    }

    @Override
    @Nonnull
    public EnumRarity getRarity(ItemStack itemStack) {
        return this.itemRepresentation.getRarity();
    }

    @Override
    public float getSmeltingExperience(ItemStack itemStack) {
        return this.itemRepresentation.getSmeltingExperience();
    }

    @Override
    public boolean isBeaconPayment(ItemStack itemStack) {
        return this.itemRepresentation.isBeaconPayment();
    }

    @Nonnull
    @Override
    @SideOnly(Side.CLIENT)
    public CreativeTabs getCreativeTab() {
        return this.creativeTab;
    }

    @Override
    @Nonnull
    public Item setCreativeTab(@Nonnull CreativeTabs creativeTab) {
        this.creativeTab = creativeTab;
        return this;
    }
}
