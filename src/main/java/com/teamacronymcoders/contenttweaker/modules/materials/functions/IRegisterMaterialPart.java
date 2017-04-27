package com.teamacronymcoders.contenttweaker.modules.materials.functions;

import com.teamacronymcoders.contenttweaker.modules.materials.materialparts.IMaterialPart;
import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("mods.contenttweaker.RegisterMaterialPart")
public interface IRegisterMaterialPart {
    void register(IMaterialPart materialPart);
}
