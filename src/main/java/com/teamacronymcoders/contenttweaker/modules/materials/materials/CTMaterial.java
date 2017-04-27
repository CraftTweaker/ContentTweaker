package com.teamacronymcoders.contenttweaker.modules.materials.materials;

import com.teamacronymcoders.base.materialsystem.MaterialException;
import com.teamacronymcoders.base.materialsystem.materials.Material;
import com.teamacronymcoders.contenttweaker.modules.materials.materialparts.CTMaterialPart;
import com.teamacronymcoders.contenttweaker.modules.materials.materialparts.IMaterialPart;

import java.util.List;
import java.util.stream.Collectors;

public class CTMaterial implements IMaterial {
    private Material material;

    public CTMaterial(Material material) {
        this.material = material;
    }

    @Override
    public String getName() {
        return this.material.getName();
    }

    @Override
    public int getColor() {
        return this.material.getColor().getRGB();
    }

    @Override
    public boolean isHasEffect() {
        return false;
    }

    @Override
    public String getUnlocalizedName() {
        return null;
    }

    @Override
    public List<IMaterialPart> registerPartsFor(String... partNames) throws MaterialException {
        return this.material.registerPartsFor(partNames).stream().map(CTMaterialPart::new).collect(Collectors.toList());
    }
}
