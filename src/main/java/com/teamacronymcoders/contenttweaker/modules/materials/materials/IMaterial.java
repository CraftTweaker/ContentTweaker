package com.teamacronymcoders.contenttweaker.modules.materials.materials;

import com.teamacronymcoders.base.materialsystem.MaterialException;
import com.teamacronymcoders.contenttweaker.modules.materials.materialparts.IMaterialPart;

import java.util.List;

public interface IMaterial {
    String getName();

    int getColor();

    boolean isHasEffect();

    String getUnlocalizedName();

    List<IMaterialPart> registerPartsFor(String... partNames) throws MaterialException;
}
