package com.blamejared.contenttweaker.blocks.types.basic;

import com.blamejared.contenttweaker.*;
import com.blamejared.contenttweaker.api.*;
import com.blamejared.contenttweaker.blocks.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.impl.util.*;
import org.openzen.zencode.java.*;

@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.block.basic.BuilderBasic")
public class BuilderBasic implements IIsBuilder {
    
    private final BlockBuilder builder;
    
    public BuilderBasic(BlockBuilder builder) {
        this.builder = builder;
    }
    
    @Override
    public void build(MCResourceLocation location) {
        VanillaFactory.registerBlock(new CoTBlockBasic(builder.getBlockProperties(), builder.getItemProperties(), location
                .getInternal()));
    }
}
