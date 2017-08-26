package com.teamacronymcoders.contenttweaker.modules.materials;

import com.teamacronymcoders.contenttweaker.modules.materials.materialpartdata.IMaterialPartData;
import com.teamacronymcoders.contenttweaker.modules.materials.materialparts.CTMaterialPart;
import com.teamacronymcoders.contenttweaker.modules.materials.materialparts.IMaterialPart;
import com.teamacronymcoders.contenttweaker.modules.materials.materials.IMaterial;
import com.teamacronymcoders.contenttweaker.modules.materials.parts.IPart;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.item.MCItemStack;

public class MaterialPartInfo extends MCItemStack implements IMaterialPart {
    private CTMaterialPart materialPart;

    public MaterialPartInfo(CTMaterialPart materialPart) {
        super(CraftTweakerMC.getItemStack(materialPart.getItemStack()));
        this.materialPart = materialPart;
    }

    @Override
    public String getLocalizedName() {
        return materialPart.getLocalizedName();
    }

    @Override
    public boolean hasEffect() {
        return  materialPart.hasEffect();
    }

    @Override
    public IMaterial getMaterial() {
        return  materialPart.getMaterial();
    }

    @Override
    public IPart getPart() {
        return materialPart.getPart();
    }

    @Override
    public IItemStack getItemStack() {
        return materialPart.getItemStack();
    }

    @Override
    public String getTextureLocation() {
        return materialPart.getTextureLocation();
    }

    @Override
    public void setTextureLocation(String textureLocation) {
        materialPart.setTextureLocation(textureLocation);
    }

    @Override
    public int getColor() {
        return materialPart.getColor();
    }

    @Override
    public boolean isColorized() {
        return materialPart.isColorized();
    }

    @Override
    public void setColorized(boolean colorized) {
        materialPart.setColorized(colorized);
    }

    @Override
    public IMaterialPartData getData() {
        return materialPart.getData();
    }
}
