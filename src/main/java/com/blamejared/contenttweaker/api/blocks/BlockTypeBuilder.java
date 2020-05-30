package com.blamejared.contenttweaker.api.blocks;

import com.blamejared.contenttweaker.api.*;
import com.blamejared.contenttweaker.blocks.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import org.openzen.zencode.java.*;

/**
 * Denotes a special builder that is used for building special block types.
 * Used in {@link BlockBuilder#withType}
 *
 * @docParam this new BlockBuilder().withType<BlockBuilderBasic>()
 */
@ZenRegister
@Document("mods/contenttweaker/API/block/BlockTypeBuilder")
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
