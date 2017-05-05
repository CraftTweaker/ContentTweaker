package com.teamacronymcoders.contenttweaker.modules.materials.materials;

import com.teamacronymcoders.base.materialsystem.MaterialException;
import com.teamacronymcoders.contenttweaker.modules.materials.materialparts.IMaterialPart;
import com.teamacronymcoders.contenttweaker.modules.materials.parts.IPart;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.List;

@ZenClass("mods.contenttweaker.Material")
public interface IMaterial {
    @ZenGetter("name")
    String getName();

    @ZenGetter("color")
    int getColor();

    @ZenGetter("hasEffect")
    boolean isHasEffect();

    @ZenGetter("unlocalizedName")
    String getUnlocalizedName();

    @ZenMethod
    List<IMaterialPart> registerParts(String... partNames) throws MaterialException;

    @ZenMethod
    List<IMaterialPart> registerParts(IPart... parts) throws MaterialException;
}
