package com.teamacronymcoders.contenttweaker.modules.vanilla.resource.material;

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
