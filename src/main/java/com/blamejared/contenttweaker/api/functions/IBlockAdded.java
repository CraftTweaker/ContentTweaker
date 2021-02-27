package com.blamejared.contenttweaker.api.functions;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.openzen.zencode.java.ZenCodeType;

@FunctionalInterface
@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.functions.IBlockAdded")
@Document("mods/contenttweaker/API/functions/IBlockAdded")
public interface IBlockAdded extends ICotFunction {
    void apply(BlockState state, World world, BlockPos pos, BlockState oldState, boolean isMoving);
}
