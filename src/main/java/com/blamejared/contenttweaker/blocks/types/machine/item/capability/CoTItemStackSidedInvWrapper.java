package com.blamejared.contenttweaker.blocks.types.machine.item.capability;

import net.minecraft.inventory.*;
import net.minecraft.util.*;
import net.minecraftforge.items.wrapper.*;

import javax.annotation.*;

public class CoTItemStackSidedInvWrapper extends SidedInvWrapper {
    
    private final ItemHandlerInventory handler;
    
    public CoTItemStackSidedInvWrapper(ItemHandlerInventory inv, @Nullable Direction side) {
        super(inv, side);
        handler = inv;
    }
    
    @Override
    public int getSlotLimit(int slot) {
        final int slot1 = getSlot(inv, slot, side);
        return handler.getMaxStackSize(slot1);
    }
}
