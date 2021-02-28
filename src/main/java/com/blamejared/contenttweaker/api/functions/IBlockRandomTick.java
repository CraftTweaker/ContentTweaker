package com.blamejared.contenttweaker.api.functions;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Random;

@FunctionalInterface
@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.functions.IBlockRandomTick")
@Document("mods/contenttweaker/API/functions/IBlockRandomTick")
public interface IBlockRandomTick extends ICotFunction {
    @ZenCodeType.Method
    void apply(BlockState state, ServerWorld world, BlockPos pos, Random random);
}
