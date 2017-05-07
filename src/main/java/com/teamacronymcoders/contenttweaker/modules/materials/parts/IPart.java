package com.teamacronymcoders.contenttweaker.modules.materials.parts;

import com.teamacronymcoders.contenttweaker.modules.materials.materialpartdata.IPartDataPiece;
import com.teamacronymcoders.contenttweaker.modules.materials.parttypes.IPartType;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.List;

@ZenClass("mods.contenttweaker.Part")
public interface IPart {
    @ZenMethod
    String getName();

    @ZenMethod
    String getUnlocalizedName();

    @ZenMethod
    IPartType getPartType();

    @ZenMethod
    String getPartTypeName();

    @ZenMethod
    String getOreDictPrefix();

    @ZenMethod
    List<IPartDataPiece> getData();
}
