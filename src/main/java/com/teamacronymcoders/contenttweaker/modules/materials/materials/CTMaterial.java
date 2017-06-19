package com.teamacronymcoders.contenttweaker.modules.materials.materials;

import com.teamacronymcoders.base.materialsystem.MaterialException;
import com.teamacronymcoders.base.materialsystem.materialparts.MaterialPart;
import com.teamacronymcoders.base.materialsystem.materials.Material;
import com.teamacronymcoders.contenttweaker.modules.materials.CTMaterialSystem;
import com.teamacronymcoders.contenttweaker.modules.materials.materialparts.CTMaterialPart;
import com.teamacronymcoders.contenttweaker.modules.materials.materialparts.IMaterialPart;
import com.teamacronymcoders.contenttweaker.modules.materials.parts.IPart;

import java.util.Arrays;
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
    public List<IMaterialPart> registerParts(String[] partNames) throws MaterialException {
        List<MaterialPart> materialParts = CTMaterialSystem.registerPartsForMaterial(this.material, partNames);
        return materialParts.stream().map(CTMaterialPart::new).collect(Collectors.toList());
    }

    @Override
    public List<IMaterialPart> registerParts(IPart[] parts) throws MaterialException {
        List<String> names = Arrays.stream(parts).map(IPart::getName).collect(Collectors.toList());
        return this.registerParts(names.toArray(new String[names.size()]));
    }

    @Override
    public IMaterialPart registerPart(String partName) throws MaterialException {
        return this.registerParts(new String[] {partName}).get(0);
    }

    @Override
    public IMaterialPart registerPart(IPart part) throws MaterialException {
        return this.registerParts(new IPart[] {part}).get(0);
    }
}
