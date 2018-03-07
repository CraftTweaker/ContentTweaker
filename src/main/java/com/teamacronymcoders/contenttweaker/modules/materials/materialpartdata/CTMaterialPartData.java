package com.teamacronymcoders.contenttweaker.modules.materials.materialpartdata;

import com.teamacronymcoders.base.materialsystem.partdata.DataPartParsers;
import com.teamacronymcoders.base.materialsystem.partdata.MaterialPartData;

public class CTMaterialPartData implements IMaterialPartData {
    private MaterialPartData materialPartData;

    public CTMaterialPartData(MaterialPartData materialPartData) {
        this.materialPartData = materialPartData;
    }

    @Override
    public void addDataValue(String name, String value) {
        this.materialPartData.addDataValue(name, value);
    }

    @Override
    public int getIntValue(String name, int defaultValue) {
        return materialPartData.getValue(name, defaultValue, DataPartParsers::getInt);
    }

    @Override
    public String getStringValue(String name, String defaultValue) {
        return materialPartData.getValue(name, defaultValue, DataPartParsers::getString);
    }


}
