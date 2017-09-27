package com.teamacronymcoders.contenttweaker.modules.vanilla.fluids;

import com.teamacronymcoders.contenttweaker.api.utils.CTUtils;
import net.minecraft.item.EnumRarity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class FluidContent extends Fluid {
    private FluidRepresentation fluidRepresentation;

    public FluidContent(FluidRepresentation fluidRepresentation) {
        super(fluidRepresentation.getUnlocalizedName(), new ResourceLocation(fluidRepresentation.getStillLocation()),
                new ResourceLocation(fluidRepresentation.getFlowingLocation()));
        this.fluidRepresentation = fluidRepresentation;
        this.setValues();
    }

    private void setValues() {
        this.setDensity(this.fluidRepresentation.getDensity());
        this.setLuminosity(this.fluidRepresentation.getLuminosity());
        this.setEmptySound(this.fluidRepresentation.getEmptySound().getInternal());
        this.setFillSound(this.fluidRepresentation.getFillSound().getInternal());
        this.setGaseous(this.fluidRepresentation.isGaseous());
        this.setViscosity(this.fluidRepresentation.getViscosity());
        this.setRarity(CTUtils.getEnum(this.fluidRepresentation.getRarity(), EnumRarity.class));
        this.setTemperature(this.fluidRepresentation.getTemperature());
    }

    @Override
    public int getColor() {
        return this.fluidRepresentation.isColorize() ? this.fluidRepresentation.getColor() : super.getColor();
    }

    @Override
    public boolean doesVaporize(FluidStack fluidStack) {
        return this.fluidRepresentation.isVaporize();
    }
}
