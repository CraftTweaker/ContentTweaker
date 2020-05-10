package com.blamejared.contenttweaker.zencode;

import com.blamejared.contenttweaker.blocks.types.machine.item.builder.*;
import com.blamejared.crafttweaker.api.annotations.*;
import org.openzen.zencode.java.*;

@ZenRegister
@ZenCodeType.Expansion("string")
public class ExpandString {
    
    @ZenCodeType.Caster(implicit = true)
    public static SlotSelector asSlotSelector(String _this) {
        return slot -> slot.isInGroup(_this);
    }
}
