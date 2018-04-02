package com.teamacronymcoders.contenttweaker.modules.tinkers.materials;

import com.teamacronymcoders.contenttweaker.modules.tinkers.utils.Functions;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.liquid.MCLiquidStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.materials.Material;

import javax.annotation.Nonnull;

public class CoTTConMaterial extends Material {

    public boolean hidden = false;
    public IItemStack representativeItem = null;
    public IItemStack shard = null;
    public ILiquidStack liquid = null;
    public String localizedName = null;
    public Functions.ItemLocalizer itemLocalizer = null;
    final TConMaterialRepresentation thisMaterial = new TConMaterialRepresentation(this);

    public CoTTConMaterial(String identifier, int color) {
        super(identifier, color);
    }

    public void addItemMatch(RecipeMatch recipeMatch) {
        items.add(recipeMatch);
    }

    @Override
    public boolean isHidden() {
        return hidden;
    }

    @Override
    public ItemStack getRepresentativeItem() {
        return CraftTweakerMC.getItemStack(representativeItem);
    }

    @Override
    public void setRepresentativeItem(ItemStack representativeItem) {
        this.representativeItem = CraftTweakerMC.getIItemStack(representativeItem);
    }

    @Override
    public void setShard(@Nonnull ItemStack stack) {
        this.shard = CraftTweakerMC.getIItemStack(stack);
    }

    @Override
    public ItemStack getShard() {
        return CraftTweakerMC.getItemStack(shard);
    }

    @Override
    public boolean hasFluid() {
        return liquid != null;
    }

    @Override
    public CoTTConMaterial setFluid(Fluid liquid) {
        this.liquid = new MCLiquidStack(new FluidStack(liquid, 1));
        return this;
    }

    @Override
    public String getLocalizedName() {
        if (localizedName != null) {
            return localizedName;
        }
        return super.getLocalizedName();
    }

    @Override
    public String getLocalizedItemName(String itemName) {
        if (itemLocalizer != null) {
            return itemLocalizer.handle(thisMaterial, itemName);
        }
        return super.getLocalizedItemName(itemName);
    }

    @Override
    public Fluid getFluid() {
        return liquid == null ? null : CraftTweakerMC.getFluid(liquid.getDefinition());
    }
}
