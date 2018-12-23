package com.teamacronymcoders.contenttweaker.modules.materials.materialpartdata;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.contenttweaker.MaterialPartData")
public interface IMaterialPartData {
    @ZenMethod
    void addDataValue(String name, String value);

    @ZenMethod
    int getIntValue(String name, int defaultValue);

    @ZenMethod
    String getStringValue(String name, String defaultValue);
}
