package com.blamejared.contenttweaker.api.functions;

import com.blamejared.contenttweaker.blocks.types.advanced.CoTBlockAdvanced;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.util.MCDirection;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.block.BlockState;
import org.openzen.zencode.java.ZenCodeType;

@FunctionalInterface
@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.functions.IBlockIsSideInvisible")
@Document("mods/contenttweaker/API/functions/IBlockIsSideInvisible")
public interface IBlockIsSideInvisible extends ICotFunction {
    
    @ZenCodeType.Field
    IBlockIsSideInvisible GLASS_LIKE = (thisBlock, state, adjacentBlockState, side) -> adjacentBlockState.isIn(thisBlock);
    
    @ZenCodeType.Method
    boolean apply(CoTBlockAdvanced thisBlock, BlockState state, BlockState adjacentBlockState, MCDirection side);
}
