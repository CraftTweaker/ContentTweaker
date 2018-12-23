package com.teamacronymcoders.contenttweaker.modules.materials.functions;

import com.teamacronymcoders.contenttweaker.modules.materials.materialparts.IMaterialPart;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("mods.contenttweaker.RegisterMaterialPart")
public interface IRegisterMaterialPart {
    void register(IMaterialPart materialPart);
}
