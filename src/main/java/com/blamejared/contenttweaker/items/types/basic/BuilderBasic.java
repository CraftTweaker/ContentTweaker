package com.blamejared.contenttweaker.items.types.basic;

import com.blamejared.contenttweaker.*;
import com.blamejared.contenttweaker.api.*;
import com.blamejared.contenttweaker.items.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.impl.util.*;
import org.openzen.zencode.java.*;

@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.item.basic.BuilderBasic")
public class BuilderBasic implements IIsBuilder {
    
    private final ItemBuilder builder;
    
    public BuilderBasic(ItemBuilder builder) {
        this.builder = builder;
    }
    
    @Override
    public void build(MCResourceLocation location) {
        VanillaFactory.registerItem(new CoTItemBasic(builder.getItemProperties(), location.getInternal()));
    }
}
