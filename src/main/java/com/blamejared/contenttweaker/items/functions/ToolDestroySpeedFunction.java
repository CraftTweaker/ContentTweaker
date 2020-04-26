package com.blamejared.contenttweaker.items.functions;

import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.api.item.*;
import com.blamejared.crafttweaker.impl.blocks.*;
import com.blamejared.crafttweaker.impl.item.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import org.openzen.zencode.java.*;

@ZenRegister
@FunctionalInterface
@ZenCodeType.Name("mods.contenttweaker.item.ToolDestroySpeedFunction")
@Document("mods/contenttweaker/item/functions/ToolDestroySpeedFunction")
public interface ToolDestroySpeedFunction {
    
    @ZenCodeType.Method
    float getDestroySpeed(IItemStack stack, MCBlockState state);
}
