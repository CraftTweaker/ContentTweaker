package com.blamejared.contenttweaker.api.blocks;

import com.blamejared.contenttweaker.api.*;
import com.blamejared.contenttweaker.blocks.*;
import com.blamejared.crafttweaker.api.annotations.*;
import org.openzen.zencode.java.*;

@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.block.BlockTypeBuilder")
public abstract class BlockTypeBuilder implements IIsBuilder {
    protected final BlockBuilder blockBuilder;
    
    protected BlockTypeBuilder(BlockBuilder blockBuilder) {
        this.blockBuilder = blockBuilder;
    }
    
    public BlockBuilder getBlockBuilder() {
        return blockBuilder;
    }
}
