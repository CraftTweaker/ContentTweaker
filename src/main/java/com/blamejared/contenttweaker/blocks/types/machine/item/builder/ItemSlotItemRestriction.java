package com.blamejared.contenttweaker.blocks.types.machine.item.builder;

import com.blamejared.crafttweaker.impl.item.*;

import java.util.function.*;

public class ItemSlotItemRestriction {
    private int maxStackSize;
    private Predicate<? super MCItemStackMutable> canOutputPredicate;
    private Predicate<? super MCItemStackMutable> canInputPredicate;
    
    public ItemSlotItemRestriction() {
        this.canInputPredicate = mcItemStackMutable -> true;
        this.canOutputPredicate = this.canInputPredicate;
    }
    
    void setMaxStackSize(int maxStackSize) {
        this.maxStackSize = maxStackSize;
    }
    
    void setCanInputPredicate(Predicate<? super MCItemStackMutable> canInputPredicate) {
        this.canInputPredicate = canInputPredicate;
    }
    
    void setCanOutputPredicate(Predicate<? super MCItemStackMutable> canOutputPredicate) {
        this.canOutputPredicate = canOutputPredicate;
    }
    
    public int getMaxStackSize() {
        return maxStackSize;
    }
    
    public boolean canOutput(MCItemStackMutable mcItemStackMutable) {
        return !mcItemStackMutable.isEmpty() && canOutputPredicate.test(mcItemStackMutable);
    }
    
    public boolean acceptsItemInput(MCItemStackMutable mcItemStackMutable) {
        return !mcItemStackMutable.isEmpty() && canInputPredicate.test(mcItemStackMutable);
    }
}
