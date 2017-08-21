package com.teamacronymcoders.contenttweaker.modules.materials.parts;

import com.teamacronymcoders.base.materialsystem.MaterialException;
import com.teamacronymcoders.base.materialsystem.MaterialUser;
import com.teamacronymcoders.base.materialsystem.parts.PartBuilder;
import com.teamacronymcoders.base.materialsystem.parttype.PartType;
import com.teamacronymcoders.contenttweaker.modules.materials.parttypes.IPartType;

public class CTPartBuilder implements IPartBuilder {
    private PartBuilder partBuilder;
    
    public CTPartBuilder(MaterialUser user) {
        this.partBuilder = new PartBuilder(user);
    }
    
    @Override
    public IPartBuilder setName(String name) {
        this.partBuilder.setName(name);
        return this;
    }

    @Override
    public IPartBuilder setPartType(IPartType partType) {
        if (partType.getInternal() instanceof PartType) {
            this.partBuilder.setPartType((PartType) partType.getInternal());
        }
        return this;
    }

    @Override
    public IPart build() throws MaterialException {
        return new CTPart(this.partBuilder.build());
    }
}
