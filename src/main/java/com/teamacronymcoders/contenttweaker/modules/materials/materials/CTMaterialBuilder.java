package com.teamacronymcoders.contenttweaker.modules.materials.materials;

import com.teamacronymcoders.base.materialsystem.MaterialException;
import com.teamacronymcoders.base.materialsystem.materials.MaterialBuilder;

import java.awt.*;

public class CTMaterialBuilder implements IMaterialBuilder {
    private MaterialBuilder materialBuilder;

    public CTMaterialBuilder() {
        this.materialBuilder = new MaterialBuilder();
    }

    @Override
    public IMaterialBuilder setName(String name) {
        this.materialBuilder.setName(name);
        return this;
    }

    @Override
    public IMaterialBuilder setUnlocalizedName(String unlocalizedName) {
        this.materialBuilder.setUnlocalizedName(unlocalizedName);
        return this;
    }

    @Override
    public IMaterialBuilder setColor(int color) {
        this.materialBuilder.setColor(new Color(color));
        return this;
    }

    @Override
    public IMaterialBuilder setHasEffect(boolean hasEffect) {
        this.materialBuilder.setHasEffect(hasEffect);
        return this;
    }

    @Override
    public IMaterial createMaterial() throws MaterialException {
        return new CTMaterial(this.materialBuilder.createMaterial());
    }
}
