package com.teamacronymcoders.contenttweaker.modules.vanilla.resources.materials;

public interface IMaterialDefinition {
    String getName();

    boolean isLiquid();

    boolean isSolid();

    boolean blocksLight();

    boolean blocksMovement();

    boolean getCanBurn();

    boolean isReplaceable();

    boolean isOpaque();

    boolean isToolNotRequired();

    String getMobilityFlag();

    Object getInternal();
}
