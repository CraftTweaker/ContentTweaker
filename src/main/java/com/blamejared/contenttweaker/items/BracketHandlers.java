package com.blamejared.contenttweaker.items;

import com.blamejared.contenttweaker.items.wrappers.*;
import com.blamejared.crafttweaker.api.annotations.*;
import net.minecraft.item.*;
import net.minecraftforge.common.*;
import org.openzen.zencode.java.*;

import java.util.*;

@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.BracketHandlers")
public class BracketHandlers {
    
    @ZenCodeType.Method
    @BracketResolver("itemgroup")
    public static MCItemGroup getItemGroup(String tokens) {
        return Arrays.stream(ItemGroup.GROUPS)
                .filter(g -> g.getPath().equals(tokens))
                .map(MCItemGroup::new)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Could not find itemgroup for '<itemgroup:" + tokens + ">'!"));
    }
    
    @ZenCodeType.Method
    @BracketResolver("tooltype")
    public static MCToolType getToolType(String tokens) {
        return new MCToolType(ToolType.get(tokens));
    }
}
