package com.teamacronymcoders.contenttweaker.modules.materials;

import com.teamacronymcoders.base.materialsystem.MaterialSystem;
import com.teamacronymcoders.base.materialsystem.parts.PartDataPiece;
import com.teamacronymcoders.base.materialsystem.parttype.PartType;
import com.teamacronymcoders.contenttweaker.modules.materials.functions.IRegisterMaterialPart;
import com.teamacronymcoders.contenttweaker.modules.materials.materialpartdata.CTPartDataPiece;
import com.teamacronymcoders.contenttweaker.modules.materials.materialpartdata.IPartDataPiece;
import com.teamacronymcoders.contenttweaker.modules.materials.materialparts.CTMaterialPart;
import com.teamacronymcoders.contenttweaker.modules.materials.materials.CTMaterial;
import com.teamacronymcoders.contenttweaker.modules.materials.materials.CTMaterialBuilder;
import com.teamacronymcoders.contenttweaker.modules.materials.materials.IMaterial;
import com.teamacronymcoders.contenttweaker.modules.materials.materials.IMaterialBuilder;
import com.teamacronymcoders.contenttweaker.modules.materials.parts.CTPart;
import com.teamacronymcoders.contenttweaker.modules.materials.parts.CTPartBuilder;
import com.teamacronymcoders.contenttweaker.modules.materials.parts.IPart;
import com.teamacronymcoders.contenttweaker.modules.materials.parts.IPartBuilder;
import com.teamacronymcoders.contenttweaker.modules.materials.parttypes.CTPartType;
import com.teamacronymcoders.contenttweaker.modules.materials.parttypes.IPartType;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.contenttweaker.MaterialSystem")
public class CTMaterialSystem {
    public static MaterialSystem materialSystem;

    @ZenMethod
    public static IPartType createPartType(String name, IRegisterMaterialPart registerMaterialPart) {
        return new CTPartType(new PartType(name, register -> registerMaterialPart.register(new CTMaterialPart(register))));
    }

    @ZenMethod
    public static IPartDataPiece createPartDataPiece(String name, boolean required) {
        return new CTPartDataPiece(new PartDataPiece(name, required));
    }

    @ZenMethod
    public static IPartType getPartType(String name) {
        return new CTPartType(materialSystem.getPartType(name));
    }

    @ZenMethod
    public static IMaterialBuilder getMaterialBuilder() {
        return new CTMaterialBuilder(materialSystem);
    }

    @ZenMethod
    public static IPartBuilder getPartBuilder() {
        return new CTPartBuilder(materialSystem);
    }

    @ZenMethod
    public static IPart getPart(String name) {
        return new CTPart(materialSystem.getPart(name));
    }

    @ZenMethod
    public static IMaterial getMaterial(String name) {
        return new CTMaterial(materialSystem.getMaterial(name));
    }
}
