package com.teamacronymcoders.contenttweaker.modules.vanilla.resource;

import net.minecraft.block.material.EnumPushReaction;

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
}
