package com.teamacronymcoders.contenttweaker.modules.materials.materials;

import com.teamacronymcoders.base.materialsystem.MaterialException;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.contenttweaker.MaterialBuilder")
public interface IMaterialBuilder {
    @ZenMethod
    IMaterialBuilder setName(String name);

    @ZenMethod
    IMaterialBuilder setUnlocalizedName(String unlocalizedName);

    @ZenMethod
    IMaterialBuilder setColor(int color);

    @ZenMethod
    IMaterialBuilder setHasEffect(boolean hasEffect);

    @ZenMethod
    IMaterial createMaterial() throws MaterialException;
}
