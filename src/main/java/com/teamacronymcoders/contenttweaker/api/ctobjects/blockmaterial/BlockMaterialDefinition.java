package com.teamacronymcoders.contenttweaker.api.ctobjects.blockmaterial;

import com.teamacronymcoders.contenttweaker.api.ctobjects.enums.PushReaction;
import net.minecraft.block.material.Material;

public class BlockMaterialDefinition implements IBlockMaterialDefinition {
    private Material material;

    public BlockMaterialDefinition(Material material) {
        this.material = material;
    }

    @Override
    public String getName() {
        return this.material.getClass().getName();
    }

    @Override
    public boolean isLiquid() {
        return this.material.isLiquid();
    }

    @Override
    public boolean isSolid() {
        return this.material.isSolid();
    }

    @Override
    public boolean blocksLight() {
        return this.material.blocksLight();
    }

    @Override
    public boolean blocksMovement() {
        return this.material.blocksMovement();
    }

    @Override
    public boolean getCanBurn() {
        return this.material.getCanBurn();
    }

    @Override
    public boolean isReplaceable() {
        return this.material.isReplaceable();
    }

    @Override
    public boolean isOpaque() {
        return this.material.isOpaque();
    }

    @Override
    public boolean isToolNotRequired() {
        return this.material.isToolNotRequired();
    }

    @Override
    public PushReaction getMobilityFlag() {
        return PushReaction.of(this.material.getMobilityFlag());
    }

    @Override
    public Material getInternal() {
        return material;
    }
}
