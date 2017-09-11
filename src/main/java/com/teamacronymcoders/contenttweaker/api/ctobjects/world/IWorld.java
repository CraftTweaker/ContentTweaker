package com.teamacronymcoders.contenttweaker.api.ctobjects.world;

import com.teamacronymcoders.contenttweaker.api.ICTObject;
import com.teamacronymcoders.contenttweaker.api.ctobjects.blockpos.IBlockPos;
import com.teamacronymcoders.contenttweaker.api.ctobjects.blockstate.ICTBlockState;
import crafttweaker.api.block.IBlock;
import crafttweaker.api.world.IBiome;
import net.minecraft.world.World;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.contenttweaker.World")
public interface IWorld extends ICTObject<World> {
    @ZenMethod
    boolean isRemote();

    @ZenMethod
    boolean isRaining();

    @ZenMethod
    boolean isThundering();

    @ZenMethod
    boolean setBlockState(ICTBlockState blockState, IBlockPos blockPos);

    @ZenMethod
    IBiome getBiome(IBlockPos blockPos);

    @ZenMethod
    int getMoonPhase();

    @ZenMethod
    boolean isDayTime();

    @ZenMethod
    long getWorldTime();

    @ZenMethod
    String getWorldType();

    @ZenMethod
    int getDimension();

    @ZenMethod
    boolean isSurfaceWorld();

    @ZenMethod
    String getDimensionType();
}
