package com.blamejared.contenttweaker.api.functions;

import com.blamejared.contenttweaker.blocks.types.advanced.CoTBlockAdvanced;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.util.MCDirection;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.block.BlockState;
import org.openzen.zencode.java.ZenCodeType;

@FunctionalInterface
@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.functions.IBlockIsTransparent")
@Document("mods/contenttweaker/API/functions/IBlockIsTransparent")
public interface IBlockIsTransparent extends ICotFunction {
    
    @ZenCodeType.Field
    IBlockIsTransparent ALWAYS_TRUE = (thisBlock, state) -> true;
    
    @ZenCodeType.Field
    IBlockIsTransparent ALWAYS_FALSE = (thisBlock, state) -> false;
    
    @ZenCodeType.Method
    boolean apply(CoTBlockAdvanced thisBlock, BlockState state);
}
