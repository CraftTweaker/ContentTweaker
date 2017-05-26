package com.teamacronymcoders.contenttweaker.modules.materials.parttypes;

import com.teamacronymcoders.base.materialsystem.parttype.PartType;

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
    public Object getInternal() {
        return this.partType;
    }
}
