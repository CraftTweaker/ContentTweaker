package com.teamacronymcoders.contenttweaker.modules.materials;

import com.google.common.collect.Lists;
import com.teamacronymcoders.base.materialsystem.MaterialException;
import com.teamacronymcoders.base.materialsystem.MaterialSystem;
import com.teamacronymcoders.base.materialsystem.MaterialUser;
import com.teamacronymcoders.base.materialsystem.materials.Material;
import com.teamacronymcoders.base.materialsystem.parttype.PartDataPiece;
import com.teamacronymcoders.contenttweaker.ContentTweaker;
import com.teamacronymcoders.contenttweaker.modules.materials.functions.IRegisterMaterialPart;
import com.teamacronymcoders.contenttweaker.modules.materials.materialpartdata.CTPartDataPiece;
import com.teamacronymcoders.contenttweaker.modules.materials.materialpartdata.IPartDataPiece;
import com.teamacronymcoders.contenttweaker.modules.materials.materialparts.CTMaterialPart;
import com.teamacronymcoders.contenttweaker.modules.materials.materialparts.IMaterialPart;
import com.teamacronymcoders.contenttweaker.modules.materials.materials.CTMaterial;
import com.teamacronymcoders.contenttweaker.modules.materials.materials.CTMaterialBuilder;
import com.teamacronymcoders.contenttweaker.modules.materials.materials.IMaterial;
import com.teamacronymcoders.contenttweaker.modules.materials.materials.IMaterialBuilder;
import com.teamacronymcoders.contenttweaker.modules.materials.parts.CTPart;
import com.teamacronymcoders.contenttweaker.modules.materials.parts.CTPartBuilder;
import com.teamacronymcoders.contenttweaker.modules.materials.parts.IPart;
import com.teamacronymcoders.contenttweaker.modules.materials.parts.IPartBuilder;
import com.teamacronymcoders.contenttweaker.modules.materials.parttypes.CTCreatedPartType;
import com.teamacronymcoders.contenttweaker.modules.materials.parttypes.CTPartType;
import com.teamacronymcoders.contenttweaker.modules.materials.parttypes.IPartType;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.List;
import java.util.stream.Collectors;

@ZenClass("mods.contenttweaker.MaterialSystem")
public class CTMaterialSystem {
    @ZenMethod
    public static IPartType createPartType(String name, IRegisterMaterialPart registerMaterialPart) {
        return new CTCreatedPartType(name, registerMaterialPart);
    }

    @ZenMethod
    public static IPartDataPiece createPartDataPiece(String name, boolean required) {
        return new CTPartDataPiece(new PartDataPiece(name, required));
    }

    @ZenMethod
    public static IPartType getPartType(String name) {
        return new CTPartType(MaterialSystem.getPartType(name));
    }

    @ZenMethod
    public static IMaterialBuilder getMaterialBuilder() {
        return new CTMaterialBuilder();
    }

    @ZenMethod
    public static IPartBuilder getPartBuilder() {
        return new CTPartBuilder(ContentTweaker.instance.getMaterialUser());
    }

    @ZenMethod
    public static IPart getPart(String name) {
        return new CTPart(MaterialSystem.getPart(name));
    }

    @ZenMethod
    public static IMaterial getMaterial(String name) {
        return new CTMaterial(MaterialSystem.getMaterial(name));
    }

    @ZenMethod
    public static List<IMaterialPart> registerPartsForMaterial(Material material, String[] partNames) throws MaterialException {
        List<IMaterialPart> materialParts = Lists.newArrayList();
        MaterialUser materialUser = ContentTweaker.instance.getMaterialUser();
        if (materialUser != null) {
            materialParts.addAll(materialUser.registerPartsForMaterial(material, partNames).stream()
                    .map(CTMaterialPart::new).collect(Collectors.toList()));
        }
        return materialParts;
    }
}
