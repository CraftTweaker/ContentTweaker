package com.teamacronymcoders.contenttweaker.modules.materials.brackethandler;

import com.teamacronymcoders.base.materialsystem.materialparts.MaterialPart;
import com.teamacronymcoders.contenttweaker.api.ctobjects.color.CTColor;
import com.teamacronymcoders.contenttweaker.modules.materials.materialpartdata.CTMaterialPartData;
import com.teamacronymcoders.contenttweaker.modules.materials.materialpartdata.IMaterialPartData;
import com.teamacronymcoders.contenttweaker.modules.materials.materialparts.IMaterialPart;
import com.teamacronymcoders.contenttweaker.modules.materials.materials.CTMaterial;
import com.teamacronymcoders.contenttweaker.modules.materials.materials.IMaterial;
import com.teamacronymcoders.contenttweaker.modules.materials.parts.CTPart;
import com.teamacronymcoders.contenttweaker.modules.materials.parts.IPart;
import crafttweaker.api.item.IItemStack;
import crafttweaker.mc1120.item.MCItemStack;
import net.minecraft.util.ResourceLocation;

public class MaterialPartDefinition extends MCItemStack implements IMaterialPart {
    private MaterialPart materialPart;

    public MaterialPartDefinition(MaterialPart materialPart) {
        super(materialPart.getItemStack());
        this.materialPart = materialPart;
    }

    @Override
    public String getName() {
        return materialPart.getLocalizedName();
    }

    @Override
    public String getLocalizedName() {
        return materialPart.getLocalizedName();
    }

    @Override
    public boolean hasEffect() {
        return materialPart.hasEffect();
    }

    @Override
    public IMaterial getMaterial() {
        return new CTMaterial(materialPart.getMaterial());
    }

    @Override
    public IPart getPart() {
        return new CTPart(materialPart.getPart());
    }

    @Override
    public IItemStack getItemStack() {
        return new MCItemStack(materialPart.getItemStack());
    }

    @Override
    public String getTextureLocation() {
        return materialPart.getTextureLocation().toString();
    }

    @Override
    public void setTextureLocation(String textureLocation) {
        materialPart.setTextureLocation(new ResourceLocation(textureLocation));
    }

    @Override
    public int getColor() {
        return materialPart.getColor();
    }

    @Override
    public CTColor getCTColor() {
        return CTColor.fromInt(this.getColor());
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
        return new CTMaterialPartData(materialPart.getData());
    }
}
