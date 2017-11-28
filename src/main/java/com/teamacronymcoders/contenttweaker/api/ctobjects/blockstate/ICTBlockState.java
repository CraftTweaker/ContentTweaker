package com.teamacronymcoders.contenttweaker.api.ctobjects.blockstate;

import com.teamacronymcoders.contenttweaker.api.ICTObject;
import com.teamacronymcoders.contenttweaker.api.ctobjects.blockpos.IBlockPos;
import com.teamacronymcoders.contenttweaker.api.ctobjects.enums.Facing;
import com.teamacronymcoders.contenttweaker.api.ctobjects.enums.PushReaction;
import com.teamacronymcoders.contenttweaker.api.ctobjects.world.IWorld;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlock;
import net.minecraft.block.state.IBlockState;
import stanhebben.zenscript.annotations.*;

@ZenRegister
@ZenClass("mods.contenttweaker.BlockState")
public interface ICTBlockState extends ICTObject<IBlockState> {
    @ZenMethod
    @ZenGetter("block")
    IBlock getBlock();

    @ZenMethod
    @ZenGetter("meta")
    int getMeta();

    @ZenMethod
    boolean isReplaceable(IWorld world, IBlockPos blockPos);

    @ZenMethod
    int getLightValue(IWorld world, IBlockPos blockPos);

    @ZenMethod
    boolean canProvidePower();

    @ZenMethod
    int getWeakPower(IWorld world, IBlockPos blockPos, Facing facing);

    @ZenMethod
    int getComparatorInputOverride(IWorld world, IBlockPos blockPos);

    @ZenMethod
    PushReaction getMobilityFlag();

    @ZenMethod
    @ZenOperator(OperatorType.COMPARE)
    boolean equals(ICTBlockState other);
}
