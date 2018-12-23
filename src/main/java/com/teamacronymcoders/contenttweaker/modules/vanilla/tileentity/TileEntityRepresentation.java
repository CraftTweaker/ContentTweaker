package com.teamacronymcoders.contenttweaker.modules.vanilla.tileentity;

import com.teamacronymcoders.contenttweaker.api.IRepresentation;
import stanhebben.zenscript.annotations.ZenProperty;

public class TileEntityRepresentation implements IRepresentation<TileEntityRepresentation> {
    @ZenProperty
    public String name;
    @ZenProperty
    public ITileEntityUpdate clientUpdate = (world, blockPos, tileEntityData) -> {
    };
    @ZenProperty
    public ITileEntityUpdate serverUpdate = (world, blockPos, tileEntityData) -> {
    };

    public TileEntityRepresentation(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTypeName() {
        return "tile_entity";
    }

    @Override
    public void register() {
        TileEntityContent.REPRESENTATION_MAP.put(name, this);
    }

    @Override
    public TileEntityRepresentation getInternal() {
        return this;
    }
}
