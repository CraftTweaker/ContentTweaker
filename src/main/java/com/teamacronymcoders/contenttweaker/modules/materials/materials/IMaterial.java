package com.teamacronymcoders.contenttweaker.modules.materials.materials;

import com.teamacronymcoders.base.materialsystem.MaterialException;
import com.teamacronymcoders.contenttweaker.modules.materials.materialparts.IMaterialPart;
import com.teamacronymcoders.contenttweaker.modules.materials.parts.IPart;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.List;

@ZenClass("mods.contenttweaker.Material")
public interface IMaterial {
    @ZenMethod
    String getName();

    @ZenMethod
    int getColor();

    @ZenMethod
    boolean isHasEffect();

    @ZenMethod
    String getUnlocalizedName();

    @ZenMethod
    List<IMaterialPart> registerParts(String... partNames) throws MaterialException;

    @ZenMethod
    List<IMaterialPart> registerParts(IPart... parts) throws MaterialException;

    @ZenMethod
    IMaterialPart registerPart(String partName) throws MaterialException;

    @ZenMethod
    IMaterialPart registerPart(IPart part) throws MaterialException;
}
