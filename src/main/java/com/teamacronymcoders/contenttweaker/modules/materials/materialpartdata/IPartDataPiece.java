package com.teamacronymcoders.contenttweaker.modules.materials.materialpartdata;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenClass("mods.contenttweaker.PartDataPiece")
public interface IPartDataPiece {
    @ZenGetter("name")
    String getName();

    @ZenGetter("isRequired")
    boolean isRequired();

    Object getInternal();
}
