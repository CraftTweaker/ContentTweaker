package com.teamacronymcoders.contenttweaker.modules.materials.materialpartdata;

import com.teamacronymcoders.base.materialsystem.parts.PartDataPiece;

public class CTPartDataPiece implements IPartDataPiece {
    private PartDataPiece partDataPiece;

    public CTPartDataPiece(PartDataPiece partDataPiece) {
        this.partDataPiece = partDataPiece;
    }

    @Override
    public String getName() {
        return this.partDataPiece.getName();
    }

    @Override
    public boolean isRequired() {
        return this.partDataPiece.isRequired();
    }

    @Override
    public Object getInternal() {
        return this.partDataPiece;
    }
}
