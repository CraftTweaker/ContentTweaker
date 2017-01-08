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
}
