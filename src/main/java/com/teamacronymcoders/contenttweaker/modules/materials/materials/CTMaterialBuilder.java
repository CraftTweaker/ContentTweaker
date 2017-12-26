package com.teamacronymcoders.contenttweaker.modules.materials.materials;

import com.teamacronymcoders.base.materialsystem.MaterialException;
import com.teamacronymcoders.base.materialsystem.materials.MaterialBuilder;
import com.teamacronymcoders.contenttweaker.api.ctobjects.color.CTColor;

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
    public IMaterialBuilder setColor(int color) {
        return setColor(CTColor.fromInt(color));
    }

    @Override
    public IMaterialBuilder setColor(CTColor color) {
        this.materialBuilder.setColor(new Color(color.getIntColor(), true));
        return this;
    }

    @Override
    public IMaterialBuilder setHasEffect(boolean hasEffect) {
        this.materialBuilder.setHasEffect(hasEffect);
        return this;
    }

    @Override
    public IMaterial build() throws MaterialException {
        return new CTMaterial(this.materialBuilder.build());
    }
}
