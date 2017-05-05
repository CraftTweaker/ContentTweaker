package com.teamacronymcoders.contenttweaker.modules.materials.materialparts;

import com.teamacronymcoders.base.materialsystem.materialparts.MaterialPart;
import com.teamacronymcoders.contenttweaker.modules.materials.materialpartdata.CTMaterialPartData;
import com.teamacronymcoders.contenttweaker.modules.materials.materialpartdata.IMaterialPartData;
import com.teamacronymcoders.contenttweaker.modules.materials.materials.CTMaterial;
import com.teamacronymcoders.contenttweaker.modules.materials.materials.IMaterial;
import com.teamacronymcoders.contenttweaker.modules.materials.parts.CTPart;
import com.teamacronymcoders.contenttweaker.modules.materials.parts.IPart;
import minetweaker.api.item.IItemStack;
import minetweaker.mc1102.item.MCItemStack;
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
    public boolean isColorize() {
        return this.materialPart.isColorize();
    }

    @Override
    public void setColorize(boolean colorize) {
        this.materialPart.setColorize(colorize);
    }

    @Override
    public IMaterialPartData getData() {
        return new CTMaterialPartData(this.materialPart.getData());
    }
}
