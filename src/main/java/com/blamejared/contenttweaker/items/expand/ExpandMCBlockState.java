package com.blamejared.contenttweaker.items.expand;

import com.blamejared.contenttweaker.items.wrappers.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.impl.blocks.*;
import org.openzen.zencode.java.*;

@ZenRegister
@ZenCodeType.Expansion("crafttweaker.api.block.MCBlockState")
public class ExpandMCBlockState {
    
    @ZenCodeType.Getter("harvestTool")
    @ZenCodeType.Method
    public static MCToolType getHarvestTool(MCBlockState _this) {
        return new MCToolType(_this.getInternal().getHarvestTool());
    }
}
