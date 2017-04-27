package com.teamacronymcoders.contenttweaker.modules.materials.parts;

import com.teamacronymcoders.base.materialsystem.MaterialException;
import com.teamacronymcoders.contenttweaker.modules.materials.materialpartdata.IPartDataPiece;
import com.teamacronymcoders.contenttweaker.modules.materials.parttypes.IPartType;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

import java.util.List;

@ZenClass("mods.contenttweaker.PartBuilder")
public interface IPartBuilder {
    @ZenSetter("name")
    IPartBuilder setName(String name);

    @ZenSetter("partType")
    IPartBuilder setPartType(IPartType partType);

    @ZenSetter("data")
    IPartBuilder setData(List<IPartDataPiece> data);

    @ZenMethod
    IPartBuilder addData(IPartDataPiece dataPiece);

    @ZenMethod
    IPart createPart() throws MaterialException;
}
