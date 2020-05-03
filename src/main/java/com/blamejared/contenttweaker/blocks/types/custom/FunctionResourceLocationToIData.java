package com.blamejared.contenttweaker.blocks.types.custom;

import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.api.data.*;
import com.blamejared.crafttweaker.impl.util.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import org.openzen.zencode.java.*;

@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.blocks.FunctionResourceLocationToIData")
@Document("mods/contenttweaker/blocks/FunctionResourceLocationToIData")
@FunctionalInterface
public interface FunctionResourceLocationToIData {
    
    @ZenCodeType.Method
    IData mapModel(MCResourceLocation name);
}

