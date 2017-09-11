package com.teamacronymcoders.contenttweaker.api.ctobjects.blockpos;

import com.teamacronymcoders.contenttweaker.api.ICTObject;
import net.minecraft.util.math.BlockPos;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.contenttweaker.BlockPos")
public interface IBlockPos extends ICTObject<BlockPos> {
    @ZenMethod
    int getX();

    @ZenMethod
    int getY();

    @ZenMethod
    int getZ();

    @ZenMethod
    IBlockPos getOffset(String directionName, int offset);
}
