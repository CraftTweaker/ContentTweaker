package com.teamacronymcoders.contenttweaker.modules.materials.materials;

import com.teamacronymcoders.base.materialsystem.MaterialException;
import com.teamacronymcoders.contenttweaker.api.ctobjects.color.CTColor;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.contenttweaker.MaterialBuilder")
public interface IMaterialBuilder {
    @ZenMethod
    IMaterialBuilder setName(String name);

    @ZenMethod
    IMaterialBuilder setColor(int color);

    @ZenMethod
    IMaterialBuilder setColor(CTColor color);

    @ZenMethod
    IMaterialBuilder setHasEffect(boolean hasEffect);

    @ZenMethod
    IMaterial build() throws MaterialException;
}
