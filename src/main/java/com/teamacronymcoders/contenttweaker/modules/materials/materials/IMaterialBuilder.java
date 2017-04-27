package com.teamacronymcoders.contenttweaker.modules.materials.materials;

import com.teamacronymcoders.base.materialsystem.MaterialException;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenClass("mods.contenttweaker.MaterialBuilder")
public interface IMaterialBuilder {
    @ZenSetter("name")
    IMaterialBuilder setName(String name);

    @ZenSetter("unlocalizedName")
    IMaterialBuilder setUnlocalizedName(String unlocalizedName);

    @ZenSetter("color")
    IMaterialBuilder setColor(int color);

    @ZenSetter("hasEffect")
    IMaterialBuilder setHasEffect(boolean hasEffect);

    @ZenMethod
    IMaterial createMaterial() throws MaterialException;
}
