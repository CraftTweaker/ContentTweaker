package com.blamejared.contenttweaker.blocks.types.basic;

import com.blamejared.contenttweaker.*;
import com.blamejared.contenttweaker.api.blocks.*;
import com.blamejared.contenttweaker.blocks.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.impl.util.*;
import org.openzen.zencode.java.*;

@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.block.basic.BuilderBasic")
public class BuilderBasic extends BlockTypeBuilder {
    
    
    public BuilderBasic(BlockBuilder builder) {
        super(builder);
    }
    
    @Override
    public void build(MCResourceLocation location) {
        VanillaFactory.registerBlock(new CoTBlockBasic(blockBuilder.getBlockProperties(), blockBuilder.getItemProperties(), location
                .getInternal()));
    }
}
