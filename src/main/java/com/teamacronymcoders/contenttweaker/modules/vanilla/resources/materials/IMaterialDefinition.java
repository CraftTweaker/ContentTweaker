package com.teamacronymcoders.contenttweaker.modules.vanilla.resources.materials;

import com.teamacronymcoders.contenttweaker.api.ICTObject;
import net.minecraft.block.material.Material;

public interface IMaterialDefinition extends ICTObject<Material> {
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

    Material getInternal();
}
