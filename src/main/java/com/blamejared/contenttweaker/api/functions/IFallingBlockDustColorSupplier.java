package com.blamejared.contenttweaker.api.functions;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import org.openzen.zencode.java.ZenCodeType;

@FunctionalInterface
@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.functions.IFallingBlockDustColorSupplier")
@Document("mods/contenttweaker/API/functions/IFallingBlockDustColorSupplier")
public interface IFallingBlockDustColorSupplier extends ICotFunction {

    @ZenCodeType.Method
    int apply(Block thisBlock, BlockState state, World reader, BlockPos pos);
}
