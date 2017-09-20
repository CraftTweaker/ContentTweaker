package com.teamacronymcoders.contenttweaker.modules.vanilla.fluids;

import com.teamacronymcoders.contenttweaker.api.IRepresentation;
import com.teamacronymcoders.contenttweaker.modules.vanilla.resources.sounds.ISoundEventDefinition;
import com.teamacronymcoders.contenttweaker.modules.vanilla.resources.sounds.SoundEventDefinition;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraftforge.fluids.Fluid;
import stanhebben.zenscript.annotations.ZenProperty;

public class FluidRepresentation implements IRepresentation<Fluid> {
    @ZenProperty
    public String unlocalizedName;
    @ZenProperty
    public int density = 1000;
    @ZenProperty
    public boolean gaseous = false;
    @ZenProperty
    public int luminosity = 0;
    @ZenProperty
    public int temperature = 300;
    @ZenProperty
    public int color;
    @ZenProperty
    public boolean colorize = true;
    @ZenProperty
    public boolean lightPlayerOnFire = false;
    @ZenProperty
    public String rarity = EnumRarity.COMMON.toString();
    @ZenProperty
    public int viscosity = 1000;
    @ZenProperty
    public ISoundEventDefinition fillSound = new SoundEventDefinition(SoundEvents.ITEM_BUCKET_FILL);
    @ZenProperty
    public ISoundEventDefinition emptySound = new SoundEventDefinition(SoundEvents.ITEM_BUCKET_EMPTY);
    @ZenProperty
    public boolean vaporize = false;
    @ZenProperty
    public String stillLocation = "";
    @ZenProperty
    public String flowingLocation = "";

    public FluidRepresentation(String unlocalizedName, int color) {
        this.unlocalizedName = unlocalizedName;
        this.color = color;
    }

    public String getUnlocalizedName() {
        return unlocalizedName;
    }

    public void setUnlocalizedName(String unlocalizedName) {
        this.unlocalizedName = unlocalizedName;
    }

    public int getDensity() {
        return density;
    }

    public void setDensity(int density) {
        this.density = density;
    }

    public boolean isGaseous() {
        return gaseous;
    }

    public void setGaseous(boolean gaseous) {
        this.gaseous = gaseous;
    }

    public int getLuminosity() {
        return luminosity;
    }

    public void setLuminosity(int luminosity) {
        this.luminosity = luminosity;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isLightPlayerOnFire() {
        return lightPlayerOnFire;
    }

    public void setLightPlayerOnFire(boolean lightPlayerOnFire) {
        this.lightPlayerOnFire = lightPlayerOnFire;
    }


    public boolean isColorize() {
        return colorize;
    }

    public void setColorize(boolean colorize) {
        this.colorize = colorize;
    }

    public String getStillLocation() {
        return stillLocation;
    }

    public void setStillLocation(String stillLocation) {
        this.stillLocation = stillLocation;
    }

    public String getFlowingLocation() {
        return flowingLocation;
    }

    public void setFlowingLocation(String flowingLocation) {
        this.flowingLocation = flowingLocation;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public int getViscosity() {
        return viscosity;
    }

    public void setViscosity(int viscosity) {
        this.viscosity = viscosity;
    }

    public ISoundEventDefinition getFillSound() {
        return fillSound;
    }

    public void setFillSound(ISoundEventDefinition fillSound) {
        this.fillSound = fillSound;
    }

    public ISoundEventDefinition getEmptySound() {
        return emptySound;
    }

    public void setEmptySound(ISoundEventDefinition emptySound) {
        this.emptySound = emptySound;
    }

    public boolean isVaporize() {
        return vaporize;
    }

    public void setVaporize(boolean vaporize) {
        this.vaporize = vaporize;
    }

    @Override
    public String getName() {
        return "Fluid";
    }

    @Override
    public String getTypeName() {
        return "Fluid";
    }

    @Override
    public void register() {

    }

    @Override
    public Fluid getInternal() {
        return null;
    }
}
