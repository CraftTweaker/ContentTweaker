package com.blamejared.contenttweaker.items.types.basic;

import com.blamejared.contenttweaker.*;
import com.blamejared.contenttweaker.api.items.*;
import com.blamejared.contenttweaker.items.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.impl.util.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import org.openzen.zencode.java.*;

@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.item.basic.BuilderBasic")
@Document("mods/contenttweaker/item/basic/BuilderBasic")
public class BuilderBasic extends ItemTypeBuilder {
    
    
    public BuilderBasic(ItemBuilder builder) {
        super(builder);
    }
    
    @Override
    public void build(MCResourceLocation location) {
        VanillaFactory.registerItem(new CoTItemBasic(itemBuilder.getItemProperties(), location.getInternal()));
    }
}
