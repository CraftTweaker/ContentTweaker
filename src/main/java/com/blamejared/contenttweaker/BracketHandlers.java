package com.blamejared.contenttweaker;

import com.blamejared.contenttweaker.blocks.wrappers.*;
import com.blamejared.contenttweaker.items.wrappers.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.impl.blocks.*;
import net.minecraft.block.*;
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
    
    @ZenCodeType.Method
    @BracketResolver("soundtype")
    public static MCSoundType getSoundType(String tokens) {
        final MCSoundType fromString = MCSoundType.getFromString(tokens.toUpperCase());
        if(fromString != null) {
            return fromString;
        }
        throw new IllegalArgumentException("Could not find soundtype <soundtype:" + tokens + ">!");
    }
    
    @ZenCodeType.Method
    @BracketResolver("blockmaterial")
    public static MCBlockMaterial getBlockMaterial(String tokens) {
        final MCBlockMaterial fromString = MCBlockMaterial.getFromString(tokens.toUpperCase());
        if(fromString != null) {
            return fromString;
        }
        throw new IllegalArgumentException("Could not find blockmaterial <blockmaterial:" + tokens + ">!");
    }
}
