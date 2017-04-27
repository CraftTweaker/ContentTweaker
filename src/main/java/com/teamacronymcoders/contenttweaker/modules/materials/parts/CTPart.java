package com.teamacronymcoders.contenttweaker.modules.materials.parts;

import com.teamacronymcoders.base.materialsystem.parts.Part;
import com.teamacronymcoders.base.materialsystem.parts.PartType;
import com.teamacronymcoders.contenttweaker.modules.materials.materialpartdata.IPartDataPiece;

import java.util.List;

public class CTPart implements IPart {
    private Part part;

    public CTPart(Part part) {
        this.part = part;
    }

    @Override
    public String getName() {
        return this.part.getName();
    }

    @Override
    public String getUnlocalizedName() {
        return this.part.getUnlocalizedName();
    }

    @Override
    public PartType getPartType() {
        return this.getPartType();
    }

    @Override
    public String getPartTypeName() {
        return null;
    }

    @Override
    public String getOreDictPrefix() {
        return null;
    }

    @Override
    public List<IPartDataPiece> getData() {
        return null;
    }
}
