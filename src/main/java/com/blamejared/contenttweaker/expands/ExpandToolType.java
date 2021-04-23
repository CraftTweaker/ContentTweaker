package com.blamejared.contenttweaker.expands;

import com.blamejared.contenttweaker.wrappers.MCToolType;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import net.minecraftforge.common.ToolType;
import org.openzen.zencode.java.ZenCodeType;

// TODO remove this next breaking change - now merged into CraftTweaker itself
// Doesn't need to be documented, just allows scripts that currently use MCToolType to continue functioning, so no breaking changes
@ZenCodeType.Expansion("crafttweaker.api.tool.ToolType")
@ZenRegister
public class ExpandToolType {
    
    @ZenCodeType.Caster()
    public MCToolType asMCToolType(ToolType internal){
        return new MCToolType(internal);
    }
    
}
