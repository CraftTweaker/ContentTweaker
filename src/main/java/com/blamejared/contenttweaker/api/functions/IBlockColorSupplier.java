package com.blamejared.contenttweaker.api.functions;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;
import org.openzen.zencode.java.ZenCodeType;

@FunctionalInterface
@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.functions.IBlockColorSupplier")
@Document("mods/contenttweaker/API/functions/IBlockColorSupplier")
public interface IBlockColorSupplier extends ICotFunction {
    int apply(BlockState state, @ZenCodeType.Nullable IBlockDisplayReader world, @ZenCodeType.Nullable BlockPos pos, int tintIndex);
}
