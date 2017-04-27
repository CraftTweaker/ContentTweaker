package com.teamacronymcoders.contenttweaker.modules.materials.materialpartdata;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.contenttweaker.MaterialPartData")
public interface IMaterialPartData {
    @ZenMethod
    void addDataPiece(String name, String value);
}
