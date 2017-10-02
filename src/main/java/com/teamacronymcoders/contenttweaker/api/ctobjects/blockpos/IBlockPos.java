package com.teamacronymcoders.contenttweaker.api.ctobjects.blockpos;

import com.teamacronymcoders.contenttweaker.api.ICTObject;
import crafttweaker.annotations.ZenRegister;
import net.minecraft.util.math.BlockPos;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenRegister
@ZenClass("mods.contenttweaker.BlockPos")
public interface IBlockPos extends ICTObject<BlockPos> {
    @ZenMethod
    @ZenSetter("x")
    int getX();

    @ZenMethod
    @ZenGetter("y")
    int getY();

    @ZenMethod
    @ZenGetter("z")
    int getZ();

    @ZenMethod
    IBlockPos getOffset(String directionName, int offset);
}
