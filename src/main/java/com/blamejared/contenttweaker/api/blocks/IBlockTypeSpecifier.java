package com.blamejared.contenttweaker.api.blocks;

import com.blamejared.contenttweaker.api.*;
import com.blamejared.contenttweaker.blocks.*;
import com.blamejared.crafttweaker.api.annotations.*;
import org.openzen.zencode.java.*;

@ZenRegister
@FunctionalInterface
@ZenCodeType.Name("mods.contenttweaker.block.IBlockTypeSpecifier")
public interface IBlockTypeSpecifier<T extends IIsBuilder> {
    T apply(BlockBuilder builder);
}
