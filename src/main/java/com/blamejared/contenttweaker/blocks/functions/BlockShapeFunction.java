package com.blamejared.contenttweaker.blocks.functions;

import com.blamejared.contenttweaker.blocks.wrappers.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.impl.blocks.*;
import com.blamejared.crafttweaker.impl.util.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import org.openzen.zencode.java.*;

@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.blocks.BlockShapeFunction")
@Document("mods/contenttweaker/blocks/BlockShapeFunction")
@FunctionalInterface
public interface BlockShapeFunction {
    
    @ZenCodeType.Method
    MCVoxelShape getShape(MCBlockState state, MCBlockPos pos);
}
