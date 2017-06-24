package com.teamacronymcoders.contenttweaker.modules.materials.parttypes;

import com.teamacronymcoders.contenttweaker.modules.materials.materialpartdata.IPartDataPiece;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.contenttweaker.PartType")
public interface IPartType {
    @ZenMethod
    String getName();

    @ZenMethod
    void setData(IPartDataPiece[] data);

    Object getInternal();
}
