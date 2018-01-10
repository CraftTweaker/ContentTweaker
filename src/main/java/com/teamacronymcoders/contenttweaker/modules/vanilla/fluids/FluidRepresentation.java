package com.teamacronymcoders.contenttweaker.modules.vanilla.fluids;

import com.teamacronymcoders.base.registrysystem.BlockRegistry;
import com.teamacronymcoders.contenttweaker.ContentTweaker;
import com.teamacronymcoders.contenttweaker.api.IRepresentation;
import com.teamacronymcoders.contenttweaker.api.ctobjects.blockmaterial.BlockMaterialDefinition;
import com.teamacronymcoders.contenttweaker.api.ctobjects.blockmaterial.IBlockMaterialDefinition;
import com.teamacronymcoders.contenttweaker.modules.vanilla.resources.sounds.ISoundEventDefinition;
import com.teamacronymcoders.contenttweaker.modules.vanilla.resources.sounds.SoundEventDefinition;
import crafttweaker.CraftTweakerAPI;
import net.minecraft.block.material.Material;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenProperty;

@ZenClass("mods.contenttweaker.Fluid")
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
    public String stillLocation = "contenttweaker:fluids/fluid";
    @ZenProperty
    public String flowingLocation = "contenttweaker:fluids/fluid_flow";
    @ZenProperty
    public IBlockMaterialDefinition material = new BlockMaterialDefinition(Material.WATER);

    public FluidRepresentation(String unlocalizedName, int color) {
        this.unlocalizedName = unlocalizedName;
        this.color = color;
    }

    @ZenMethod
    public String getUnlocalizedName() {
        return unlocalizedName;
    }

    @ZenMethod
    public void setUnlocalizedName(String unlocalizedName) {
        this.unlocalizedName = unlocalizedName;
    }

    @ZenMethod
    public int getDensity() {
        return density;
    }

    @ZenMethod
    public void setDensity(int density) {
        this.density = density;
    }

    @ZenMethod
    public boolean isGaseous() {
        return gaseous;
    }

    @ZenMethod
    public void setGaseous(boolean gaseous) {
        this.gaseous = gaseous;
    }

    @ZenMethod
    public int getLuminosity() {
        return luminosity;
    }

    @ZenMethod
    public void setLuminosity(int luminosity) {
        this.luminosity = luminosity;
    }

    @ZenMethod
    public int getTemperature() {
        return temperature;
    }

    @ZenMethod
    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    @ZenMethod
    public int getColor() {
        return color;
    }

    @ZenMethod
    public void setColor(int color) {
        this.color = color;
    }

    @ZenMethod
    public boolean isColorize() {
        return colorize;
    }

    @ZenMethod
    public void setColorize(boolean colorize) {
        this.colorize = colorize;
    }

    @ZenMethod
    public String getStillLocation() {
        return stillLocation;
    }

    @ZenMethod
    public void setStillLocation(String stillLocation) {
        this.stillLocation = stillLocation;
    }

    @ZenMethod
    public String getFlowingLocation() {
        return flowingLocation;
    }

    @ZenMethod
    public void setFlowingLocation(String flowingLocation) {
        this.flowingLocation = flowingLocation;
    }

    @ZenMethod
    public String getRarity() {
        return rarity;
    }

    @ZenMethod
    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    @ZenMethod
    public int getViscosity() {
        return viscosity;
    }

    @ZenMethod
    public void setViscosity(int viscosity) {
        this.viscosity = viscosity;
    }

    @ZenMethod
    public ISoundEventDefinition getFillSound() {
        return fillSound;
    }

    @ZenMethod
    public void setFillSound(ISoundEventDefinition fillSound) {
        this.fillSound = fillSound;
    }

    @ZenMethod
    public ISoundEventDefinition getEmptySound() {
        return emptySound;
    }

    @ZenMethod
    public void setEmptySound(ISoundEventDefinition emptySound) {
        this.emptySound = emptySound;
    }

    @ZenMethod
    public boolean isVaporize() {
        return vaporize;
    }

    @ZenMethod
    public void setVaporize(boolean vaporize) {
        this.vaporize = vaporize;
    }

    @ZenMethod
    public IBlockMaterialDefinition getMaterial() {
        return material;
    }

    @ZenMethod
    public void setMaterial(IBlockMaterialDefinition material) {
        if (material.isLiquid()) {
            this.material = material;
        } else {
            CraftTweakerAPI.logError("Cannot set Material for Fluid " + this.getUnlocalizedName() +
                    " as the blockmaterial does not have 'isLiquid = true'");
        }
    }

    @Override
    @ZenMethod
    public String getName() {
        return this.getUnlocalizedName();
    }

    @Override
    public String getTypeName() {
        return "Fluid";
    }

    @Override
    @ZenMethod
    public void register() {
        ContentTweaker.instance.getRegistry(BlockRegistry.class, "BLOCK").register(new BlockFluidContent(this));
    }

    @Override
    public Fluid getInternal() {
        return FluidRegistry.getFluid(this.getUnlocalizedName());
    }
}
