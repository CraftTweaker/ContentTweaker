package com.blamejared.contenttweaker.expands;

import com.blamejared.contenttweaker.wrappers.MCItemGroup;
import com.blamejared.contenttweaker.wrappers.MCToolType;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.ToolType;
import org.openzen.zencode.java.ZenCodeType;

// TODO remove this next breaking change - now merged into CraftTweaker itself
// Doesn't need to be documented, just allows scripts that currently use MCToolType to continue functioning, so no breaking changes
@ZenCodeType.Expansion("crafttweaker.api.item.ItemGroup")
@ZenRegister
public class ExpandItemGroup {
    
    @ZenCodeType.Caster()
    public static MCItemGroup asMCItemGroup(ItemGroup internal){
        return new MCItemGroup(internal);
    }
    
}
