package com.teamacronymcoders.contenttweaker.modules.materials.materialpartdata;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.contenttweaker.PartDataPiece")
public interface IPartDataPiece {
    @ZenMethod
    String getName();

    @ZenMethod
    boolean isRequired();

    Object getInternal();
}
