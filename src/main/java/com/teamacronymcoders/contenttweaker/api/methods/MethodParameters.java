package com.teamacronymcoders.contenttweaker.api.methods;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import java.util.Random;

public class MethodParameters {
    private EntityPlayer entityPlayer;
    private Entity entity;
    private IBlockState blockState;
    private BlockPos blockPos;
    private World world;
    private Explosion explosion;
    private EnumHand hand;
    private EnumFacing enumFacing;
    private Float fallDistance;
    private Random random;

    public EntityPlayer getEntityPlayer() {
        return entityPlayer;
    }

    public void setEntityPlayer(EntityPlayer entityPlayer) {
        this.entityPlayer = entityPlayer;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public IBlockState getBlockState() {
        return blockState;
    }

    public void setBlockState(IBlockState blockState) {
        this.blockState = blockState;
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }

    public void setBlockPos(BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Explosion getExplosion() {
        return explosion;
    }

    public void setExplosion(Explosion explosion) {
        this.explosion = explosion;
    }

    public EnumHand getHand() {
        return hand;
    }

    public void setHand(EnumHand hand) {
        this.hand = hand;
    }

    public EnumFacing getEnumFacing() {
        return enumFacing;
    }

    public void setEnumFacing(EnumFacing enumFacing) {
        this.enumFacing = enumFacing;
    }

    public Float getFallDistance() {
        return fallDistance;
    }

    public void setFallDistance(Float fallDistance) {
        this.fallDistance = fallDistance;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }
}
