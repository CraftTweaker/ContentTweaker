package com.teamacronymcoders.contenttweaker.modules.materials.materialparts;

import com.teamacronymcoders.base.materialsystem.materialparts.MaterialPart;
import com.teamacronymcoders.contenttweaker.api.ctobjects.color.CTColor;
import com.teamacronymcoders.contenttweaker.modules.materials.materialpartdata.CTMaterialPartData;
import com.teamacronymcoders.contenttweaker.modules.materials.materialpartdata.IMaterialPartData;
import com.teamacronymcoders.contenttweaker.modules.materials.materials.CTMaterial;
import com.teamacronymcoders.contenttweaker.modules.materials.materials.IMaterial;
import com.teamacronymcoders.contenttweaker.modules.materials.parts.CTPart;
import com.teamacronymcoders.contenttweaker.modules.materials.parts.IPart;
import crafttweaker.api.item.IItemStack;
import crafttweaker.mc1120.item.MCItemStack;
import net.minecraft.util.ResourceLocation;

public class CTMaterialPart implements IMaterialPart {
    private MaterialPart materialPart;

    public CTMaterialPart(MaterialPart materialPart) {
        this.materialPart = materialPart;
    }

    @Override
    public String getName() {
        return this.materialPart.getLocalizedName();
    }

    @Override
    public String getLocalizedName() {
        return this.materialPart.getLocalizedName();
    }

    @Override
    public boolean hasEffect() {
        return this.materialPart.hasEffect();
    }

    @Override
    public IMaterial getMaterial() {
        return new CTMaterial(this.materialPart.getMaterial());
    }

    @Override
    public IPart getPart() {
        return new CTPart(this.materialPart.getPart());
    }

    @Override
    public IItemStack getItemStack() {
        return new MCItemStack(this.materialPart.getItemStack());
    }

    @Override
    public String getTextureLocation() {
        return this.materialPart.getTextureLocation().toString();
    }

    @Override
    public void setTextureLocation(String textureLocation) {
        this.materialPart.setTextureLocation(new ResourceLocation(textureLocation));
    }

    @Override
    public int getColor() {
        return this.materialPart.getColor();
    }

    @Override
    public CTColor getCTColor() {
        return CTColor.fromInt(this.getColor());
    }

    @Override
    public boolean isColorized() {
        return this.materialPart.isColorized();
    }

    @Override
    public void setColorized(boolean colorized) {
        this.materialPart.setColorized(colorized);
    }

    @Override
    public IMaterialPartData getData() {
        return new CTMaterialPartData(this.materialPart.getData());
    }
}
