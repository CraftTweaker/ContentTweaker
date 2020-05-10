package com.blamejared.contenttweaker.blocks.types.machine.item.capability;

import com.blamejared.contenttweaker.blocks.types.machine.capability.*;
import mcp.*;
import net.minecraft.block.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.*;
import net.minecraftforge.items.*;

import javax.annotation.*;

@MethodsReturnNonnullByDefault
public class ItemCapabilityInstance implements ICotCapabilityInstance {
    
    @CapabilityInject(IItemHandler.class)
    static Capability<IItemHandler> ITEM_HANDLER_CAPABILITY = null;
    
    private final CoTItemStackHandler handler;
    private final ItemSlotList slotList;
    
    
    public ItemCapabilityInstance(CoTCapabilityInstanceManager manager, ItemSlotList slotList, CompoundNBT nbt) {
        this.handler = new CoTItemStackHandler(manager);
        this.slotList = slotList;
        this.handler.deserializeNBT(nbt);
    }
    
    
    @Override
    @SuppressWarnings("unchecked")
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == ITEM_HANDLER_CAPABILITY) {
            return LazyOptional.of(() -> (T) handler);
        }
        
        return LazyOptional.empty();
    }
    
    @Override
    public CompoundNBT createDataCompound() {
        return handler.serializeNBT();
    }
    
    @Override
    public void deserializeDataCompound(CompoundNBT compound) {
        handler.deserializeNBT(compound);
    }
    
    @Override
    public void onBlockBroken(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
        InventoryHelper.dropItems(world, pos, handler.getStacks());
        handler.clear();
    }
    
    private static final class CoTItemStackHandler extends ItemStackHandler {
        
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
    }
}
