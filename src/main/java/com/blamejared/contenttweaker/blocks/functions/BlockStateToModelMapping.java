package com.blamejared.contenttweaker.blocks.functions;

import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.api.data.*;
import com.blamejared.crafttweaker.impl.util.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import org.openzen.zencode.java.*;

import java.util.*;

@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.blocks.BlockStateToModelMapping")
@Document("mods/contenttweaker/blocks/BlockStateToModelMapping")
@FunctionalInterface
public interface BlockStateToModelMapping {
    
    @ZenCodeType.Method
    IData mapModel(MCResourceLocation name, Map<String, String> blockValues);
}
