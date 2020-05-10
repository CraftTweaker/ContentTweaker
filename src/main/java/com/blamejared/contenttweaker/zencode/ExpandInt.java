package com.blamejared.contenttweaker.zencode;

import com.blamejared.contenttweaker.blocks.types.machine.item.builder.*;
import com.blamejared.crafttweaker.api.annotations.*;
import org.openzen.zencode.java.*;

@ZenRegister
@ZenCodeType.Expansion("int")
public class ExpandInt {
    
    @ZenCodeType.Caster(implicit = true)
    public static CoTIntRange asCoTIntRange(int _this) {
        return new CoTIntRange(_this, _this + 1);
    }
    
    @ZenCodeType.Caster(implicit = true)
    public static IndexList asIndexList(int _this) {
        return new IndexList(_this);
    }
    
    @ZenCodeType.Caster(implicit = true)
    public static SlotSelector asSlotSelector(int _this) {
        return asIndexList(_this);
    }
}
