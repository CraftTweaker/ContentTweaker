package com.teamacronymcoders.contenttweaker.modules.materials.materialpartdata;

import com.teamacronymcoders.base.materialsystem.parttype.PartDataPiece;
import com.teamacronymcoders.contenttweaker.api.ICTObject;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.contenttweaker.PartDataPiece")
public interface IPartDataPiece extends ICTObject<PartDataPiece> {
    @ZenMethod
    String getName();

    @ZenMethod
    boolean isRequired();
}
