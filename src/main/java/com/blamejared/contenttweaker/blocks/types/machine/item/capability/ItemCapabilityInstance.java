package com.blamejared.contenttweaker.blocks.types.machine.item.capability;

import com.blamejared.contenttweaker.blocks.types.machine.capability.*;
import com.blamejared.contenttweaker.blocks.types.machine.gui.*;
import mcp.*;
import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.inventory.container.*;
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
    
    @Override
    public void addToContainer(CoTContainer coTContainer, PlayerInventory playerInventory) {
        coTContainer.addSlot(new Slot(new ItemHandlerInventory(handler), 0, 0,0));
    }
    
}
