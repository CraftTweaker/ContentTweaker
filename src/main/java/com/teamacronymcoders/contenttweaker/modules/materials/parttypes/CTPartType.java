package com.teamacronymcoders.contenttweaker.modules.materials.parttypes;

import com.google.common.collect.Lists;
import com.teamacronymcoders.base.materialsystem.parttype.PartDataPiece;
import com.teamacronymcoders.base.materialsystem.parttype.PartType;
import com.teamacronymcoders.contenttweaker.modules.materials.materialpartdata.IPartDataPiece;

import java.util.Arrays;
import java.util.List;

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
        List<PartDataPiece> dataPieceList = Lists.newArrayList();
        Arrays.stream(data).forEach(dataPiece -> {
            if (dataPiece.getInternal() instanceof PartDataPiece) {
                dataPieceList.add((PartDataPiece) dataPiece);
            }
        });
        this.partType.setData(dataPieceList);
    }

    @Override
    public Object getInternal() {
        return this.partType;
    }
}
