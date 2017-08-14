package com.teamacronymcoders.contenttweaker.modules.materials.parttypes;

import com.teamacronymcoders.base.materialsystem.materialparts.MaterialPart;
import com.teamacronymcoders.base.materialsystem.parttype.PartType;
import com.teamacronymcoders.contenttweaker.modules.materials.functions.IRegisterMaterialPart;
import com.teamacronymcoders.contenttweaker.modules.materials.materialpartdata.IPartDataPiece;
import com.teamacronymcoders.contenttweaker.modules.materials.materialparts.CTMaterialPart;

import javax.annotation.Nonnull;

public class CTCreatedPartType extends PartType implements IPartType {

    private final IRegisterMaterialPart materialPartRegister;

    public CTCreatedPartType(String name, IRegisterMaterialPart materialPartRegister) {
        super(name);
        this.materialPartRegister = materialPartRegister;
    }

    @Override
    public void setup(@Nonnull MaterialPart materialPart) {
        this.materialPartRegister.register(new CTMaterialPart(materialPart));
    }

    @Override
    public void setData(IPartDataPiece[] data) {

    }

    @Override
    public Object getInternal() {
        return this;
    }
}
