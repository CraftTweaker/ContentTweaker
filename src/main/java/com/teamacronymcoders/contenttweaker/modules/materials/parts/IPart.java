package com.teamacronymcoders.contenttweaker.modules.materials.parts;

import com.teamacronymcoders.contenttweaker.modules.materials.materialpartdata.IPartDataPiece;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

import java.util.List;

@ZenClass("mods.contenttweaker.Part")
public interface IPart {
    @ZenGetter("name")
    String getName();

    @ZenGetter("unlocalizedName")
    String getUnlocalizedName();

    //PartType getPartType();

    @ZenGetter("partTypeName")
    String getPartTypeName();

    @ZenGetter("oreDictPrefix")
    String getOreDictPrefix();

    @ZenGetter("data")
    List<IPartDataPiece> getData();
}
