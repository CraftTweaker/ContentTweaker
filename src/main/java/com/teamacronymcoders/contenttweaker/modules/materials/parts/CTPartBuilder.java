package com.teamacronymcoders.contenttweaker.modules.materials.parts;

import com.teamacronymcoders.base.materialsystem.MaterialException;
import com.teamacronymcoders.base.materialsystem.parts.PartBuilder;
import com.teamacronymcoders.base.materialsystem.parttype.PartType;
import com.teamacronymcoders.contenttweaker.modules.materials.parttypes.IPartType;

public class CTPartBuilder implements IPartBuilder {
    private PartBuilder partBuilder;
    
    public CTPartBuilder() {
        this.partBuilder = new PartBuilder().setOwnerId("contenttweaker");
    }
    
    @Override
    public IPartBuilder setName(String name) {
        this.partBuilder.setName(name);
        return this;
    }

    @Override
    public IPartBuilder setPartType(IPartType partType) {
        if (partType.getInternal() != null) {
            this.partBuilder.setPartType((PartType) partType.getInternal());
        }
        return this;
    }

    @Override
    public IPartBuilder setOreDictName(String prefix) {
        this.partBuilder.setOreDictName(prefix);
        return this;
    }

    @Override
    public IPartBuilder setAdditionalOreDictNames(String... prefix) {
        this.partBuilder.setAdditionalOreDictNames(prefix);
        return this;
    }

    @Override
    public IPartBuilder setHasOverlay(boolean hasOverlay) {
        this.partBuilder.setOverlay(true);
        return this;
    }

    @Override
    public IPart build() throws MaterialException {
        return new CTPart(this.partBuilder.build());
    }
}
