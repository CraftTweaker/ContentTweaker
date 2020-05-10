package com.blamejared.contenttweaker.blocks.types.machine.item.builder;

import com.blamejared.crafttweaker.api.annotations.*;
import org.openzen.zencode.java.*;


@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.block.machine.SlotSelector")
public interface SlotSelector {
    
    static SlotSelector mergeOr(SlotSelector[] slotIndices) {
        if(slotIndices.length == 0) {
            return slot -> false;
        }
        SlotSelector out = slotIndices[0];
        for(int i = 1; i < slotIndices.length; i++) {
            out = out.or(slotIndices[i]);
        }
        return out;
    }
    
    @ZenCodeType.Method
    boolean matches(ItemCapabilitySlotBuilder slot);
    
    @ZenCodeType.Operator(ZenCodeType.OperatorType.OR)
    default SlotSelector or(SlotSelector other) {
        return slot -> this.matches(slot) || other.matches(slot);
    }
    
    @ZenCodeType.Operator(ZenCodeType.OperatorType.AND)
    default SlotSelector and(SlotSelector other) {
        return slot -> this.matches(slot) && other.matches(slot);
    }
    
    @ZenCodeType.Operator(ZenCodeType.OperatorType.NOT)
    default SlotSelector not() {
        return slot -> !this.matches(slot);
    }
}
