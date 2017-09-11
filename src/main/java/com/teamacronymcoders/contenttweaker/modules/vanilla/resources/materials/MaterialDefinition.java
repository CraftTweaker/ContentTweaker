package com.teamacronymcoders.contenttweaker.modules.vanilla.resources.materials;

import net.minecraft.block.material.Material;

public class MaterialDefinition implements IMaterialDefinition {
    private Material material;

    public MaterialDefinition(Material material) {
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
    public String getMobilityFlag() {
        return this.material.getMobilityFlag().name();
    }

    @Override
    public Material getInternal() {
        return material;
    }
}
