package com.blamejared.contenttweaker.blocks.types.machine.item.capability;

import com.blamejared.contenttweaker.blocks.types.machine.capability.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraftforge.items.*;

final class CoTItemStackHandler extends ItemStackHandler {
    
    private final CoTCapabilityInstanceManager manager;
    
    public CoTItemStackHandler(CoTCapabilityInstanceManager manager) {
        this.manager = manager;
    }
    
    @Override
    protected void onContentsChanged(int slot) {
        super.onContentsChanged(slot);
        manager.markDirty();
    }
    
    public NonNullList<ItemStack> getStacks() {
        return stacks;
    }
    
    public void clear() {
        this.stacks = NonNullList.withSize(stacks.size(), ItemStack.EMPTY);
    }
    
    public void markDirty() {
        manager.markDirty();
    }
}
