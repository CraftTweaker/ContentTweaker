package com.teamacronymcoders.contenttweaker.modules.materials.parttypes;

import com.teamacronymcoders.base.materialsystem.parttype.PartType;
import com.teamacronymcoders.contenttweaker.modules.materials.functions.IMaterialPartItemStackSupplier;
import com.teamacronymcoders.contenttweaker.modules.materials.materialpartdata.IPartDataPiece;

public class CTPartType implements IPartType {

    private final PartType partType;

    public CTPartType(PartType partType) {
        this.partType = partType;
    }

    @Override
    public String getName() {
        return this.partType.getName();
    }

    @Override
    public void setData(IPartDataPiece[] data) {
        throw new UnsupportedOperationException("Can't setData for Existing PartTypes");
    }

    @Override
    public PartType getInternal() {
        return this.partType;
    }
}
