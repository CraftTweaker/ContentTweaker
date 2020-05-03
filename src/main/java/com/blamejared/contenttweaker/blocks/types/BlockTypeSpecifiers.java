package com.blamejared.contenttweaker.blocks.types;

import com.blamejared.contenttweaker.api.blocks.*;
import com.blamejared.contenttweaker.blocks.types.basic.*;
import com.blamejared.contenttweaker.blocks.types.custom.*;
import com.blamejared.contenttweaker.blocks.types.horizontal.*;
import com.blamejared.contenttweaker.blocks.types.stairs.*;
import com.blamejared.crafttweaker.api.annotations.*;
import org.openzen.zencode.java.*;

@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.blocks.BlockTypeSpecifiers")
public class BlockTypeSpecifiers {
    
    @ZenCodeType.Field
    public static final IBlockTypeSpecifier<BuilderBasic> basic = BuilderBasic::new;
    @ZenCodeType.Field
    public static final IBlockTypeSpecifier<BuilderStairs> stairs = BuilderStairs::new;
    @ZenCodeType.Field
    public static final IBlockTypeSpecifier<BuilderPillarRotatable> pillarRotatable = BuilderPillarRotatable::new;
    @ZenCodeType.Field
    public static final IBlockTypeSpecifier<BuilderBlockCustom> custom = BuilderBlockCustom::new;
}
