package com.teamacronymcoders.contenttweaker.modules.materials.materialpartdata;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.contenttweaker.MaterialPartData")
public interface IMaterialPartData {
    @ZenMethod
    void addDataValue(String name, String value);
}
