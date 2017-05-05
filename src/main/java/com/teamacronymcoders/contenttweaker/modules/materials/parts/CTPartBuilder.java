package com.teamacronymcoders.contenttweaker.modules.materials.parts;

import com.teamacronymcoders.base.materialsystem.MaterialException;
import com.teamacronymcoders.base.materialsystem.MaterialSystem;
import com.teamacronymcoders.base.materialsystem.parts.PartBuilder;
import com.teamacronymcoders.base.materialsystem.parts.PartDataPiece;
import com.teamacronymcoders.base.materialsystem.parts.PartType;
import com.teamacronymcoders.contenttweaker.ContentTweaker;
import com.teamacronymcoders.contenttweaker.modules.materials.materialpartdata.IPartDataPiece;
import com.teamacronymcoders.contenttweaker.modules.materials.parttypes.IPartType;

import java.util.List;

public class CTPartBuilder implements IPartBuilder {
    private PartBuilder partBuilder;
    
    public CTPartBuilder(MaterialSystem materialSystem) {
        this.partBuilder = new PartBuilder(materialSystem);
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
    public IPartBuilder setData(List<IPartDataPiece> data) {
        data.forEach(this::addData);
        return this;
    }

    @Override
    public IPartBuilder addData(IPartDataPiece dataPiece) {
        if (dataPiece.getInternal() instanceof PartDataPiece) {
            this.partBuilder.addData(((PartDataPiece) dataPiece.getInternal()));
        } else {
            ContentTweaker.instance.getLogger().warning("Couldn't find valid PartDataPiece");
        }
        return this;
    }

    @Override
    public IPart createPart() throws MaterialException {
        return new CTPart(this.partBuilder.createPart());
    }
}
