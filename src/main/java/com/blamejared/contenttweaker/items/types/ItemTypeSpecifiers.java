package com.blamejared.contenttweaker.items.types;

import com.blamejared.contenttweaker.api.items.*;
import com.blamejared.contenttweaker.items.*;
import com.blamejared.contenttweaker.items.types.basic.*;
import com.blamejared.contenttweaker.items.types.tool.*;
import com.blamejared.crafttweaker.api.annotations.*;
import org.openzen.zencode.java.*;

@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.item.ItemTypeSpecifiers")
public class ItemTypeSpecifiers {
    @ZenCodeType.Field
    public static IItemTypeSpecifier<BuilderBasic> basic = BuilderBasic::new;
    @ZenCodeType.Field
    public static IItemTypeSpecifier<BuilderTool> tool = BuilderTool::new;
}
