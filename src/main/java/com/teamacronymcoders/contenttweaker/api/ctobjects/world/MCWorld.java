package com.teamacronymcoders.contenttweaker.api.ctobjects.world;

import com.teamacronymcoders.contenttweaker.api.ctobjects.blockpos.IBlockPos;
import com.teamacronymcoders.contenttweaker.api.ctobjects.blockstate.ICTBlockState;
import com.teamacronymcoders.contenttweaker.api.ctobjects.blockstate.MCBlockState;
import com.teamacronymcoders.contenttweaker.api.ctobjects.random.CTRandom;
import crafttweaker.api.world.IBiome;
import crafttweaker.mc1120.world.MCBiome;
import net.minecraft.world.World;

public class MCWorld implements IWorld {
    private World world;

    public MCWorld(World world) {
        this.world = world;
    }

    @Override
    public boolean isRemote() {
        return world.isRemote;
    }

    @Override
    public boolean isRaining() {
        return world.getWorldInfo().isRaining();
    }

    @Override
    public boolean isThundering() {
        return world.getWorldInfo().isThundering();
    }

    @Override
    public boolean setBlockState(ICTBlockState blockState, IBlockPos blockPos) {
        return this.world.setBlockState(blockPos.getInternal(), blockState.getInternal(), 3);
    }

    @Override
    public ICTBlockState getBlockState(IBlockPos blockPos) {
        return new MCBlockState(this.world.getBlockState(blockPos.getInternal()));
    }

    @Override
    public IBiome getBiome(IBlockPos blockPos) {
        return new MCBiome(world.getBiome(blockPos.getInternal()));
    }

    @Override
    public int getMoonPhase() {
        return this.world.provider.getMoonPhase(this.world.getWorldInfo().getWorldTime());
    }

    @Override
    public boolean isDayTime() {
        return this.world.isDaytime();
    }

    @Override
    public long getWorldTime() {
        return this.world.getWorldTime();
    }

    @Override
    public String getWorldType() {
        return this.world.getWorldType().getName();
    }

    @Override
    public int getDimension() {
        return this.world.provider.getDimension();
    }

    @Override
    public boolean isSurfaceWorld() {
        return this.world.provider.isSurfaceWorld();
    }

    @Override
    public String getDimensionType() {
        return this.world.provider.getDimensionType().getName();
    }

    @Override
    public CTRandom getRandom() {
        return new CTRandom(world.rand);
    }

    @Override
    public World getInternal() {
        return world;
    }
}
