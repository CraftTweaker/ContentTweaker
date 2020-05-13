package com.blamejared.contenttweaker.blocks.types.machine.item.capability;

import com.blamejared.contenttweaker.blocks.types.machine.*;
import com.blamejared.contenttweaker.blocks.types.machine.capability.*;
import com.blamejared.contenttweaker.blocks.types.machine.gui.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraftforge.common.util.*;
import net.minecraftforge.items.*;

import javax.annotation.*;
import java.util.*;

final class CoTItemStackHandler extends ItemStackHandler {
    
    private final CoTCapabilityInstanceManager manager;
    private final ItemHandlerInventory itemHandlerInventory;
    private final Map<Direction, LazyOptional<IItemHandlerModifiable>> sidedInventories;
    
    public CoTItemStackHandler(CoTCapabilityInstanceManager manager, ItemSlotList slotList) {
        this.manager = manager;
        this.itemHandlerInventory = new ItemHandlerInventory(this, slotList);
        this.sidedInventories = new HashMap<>();
        for(Direction value : Direction.values()) {
            sidedInventories.put(value, LazyOptional.of(() -> new CoTItemStackSidedInvWrapper(itemHandlerInventory, value)));
        }
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
    
    public void addToContainer(CoTContainer coTContainer) {
        itemHandlerInventory.addToContainer(coTContainer);
    }
    
    public LazyOptional<IItemHandlerModifiable> getItemHandlerCapability(@Nullable Direction side) {
        if(side == null) {
            return LazyOptional.of(() -> this);
        }
        return sidedInventories.get(side);
    }
    
    public boolean tick(CoTTileTicking tile) {
        return itemHandlerInventory.tick(tile);
    }
    
    @Override
    public int getSlotLimit(int slot) {
        return itemHandlerInventory.getMaxStackSize(slot);
    }
}
