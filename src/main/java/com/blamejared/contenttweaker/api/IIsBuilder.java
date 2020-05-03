package com.blamejared.contenttweaker.api;

import com.blamejared.contenttweaker.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.impl.util.*;
import org.openzen.zencode.java.*;

@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.api.IIsBuilder")
public interface IIsBuilder {
    
    @ZenCodeType.Method
    default void build(String name) {
        build(new MCResourceLocation(ContentTweaker.MOD_ID, name));
    }
    
    @ZenCodeType.Method
    void build(MCResourceLocation location);
}
