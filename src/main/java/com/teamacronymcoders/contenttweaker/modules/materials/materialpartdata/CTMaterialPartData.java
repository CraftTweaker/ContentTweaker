package com.teamacronymcoders.contenttweaker.modules.materials.materialpartdata;

import com.teamacronymcoders.base.materialsystem.materialparts.MaterialPartData;

public class CTMaterialPartData implements IMaterialPartData {
    private MaterialPartData materialPartData;

    public CTMaterialPartData(MaterialPartData materialPartData) {
        this.materialPartData = materialPartData;
    }

    @Override
    public void addDataPiece(String name, String value) {
        this.materialPartData.addDataPiece(name, value);
    }
}
