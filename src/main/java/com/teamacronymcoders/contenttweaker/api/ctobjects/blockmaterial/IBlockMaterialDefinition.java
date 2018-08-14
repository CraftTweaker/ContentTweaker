package com.teamacronymcoders.contenttweaker.api.ctobjects.blockmaterial;

import com.teamacronymcoders.contenttweaker.api.ICTObject;
import com.teamacronymcoders.contenttweaker.api.ctobjects.enums.PushReaction;
import crafttweaker.annotations.ZenRegister;
import net.minecraft.block.material.Material;
import stanhebben.zenscript.annotations.*;

@ZenRegister
@ZenClass("mods.contenttweaker.BlockMaterial")
public interface IBlockMaterialDefinition extends ICTObject<Material> {
    @ZenMethod
    @ZenGetter("name")
    String getName();

    @ZenMethod
    @ZenGetter("liquid")
    boolean isLiquid();

    @ZenMethod
    @ZenGetter("solid")
    boolean isSolid();

    @ZenMethod
    @ZenGetter("blocksLight")
    boolean blocksLight();

    @ZenMethod
    @ZenGetter("blocksMovement")
    boolean blocksMovement();

    @ZenMethod
    @ZenGetter("canBurn")
    boolean getCanBurn();

    @ZenMethod
    @ZenGetter("replaceable")
    boolean isReplaceable();

    @ZenMethod
    @ZenGetter("opaque")
    boolean isOpaque();

    @ZenMethod
    @ZenGetter("toolNotRequired")
    boolean isToolNotRequired();

    @ZenMethod
    @ZenGetter("mobilityFlag")
    PushReaction getMobilityFlag();

    @ZenOperator(OperatorType.COMPARE)
    int compare(IBlockMaterialDefinition other);

    Material getInternal();
}
