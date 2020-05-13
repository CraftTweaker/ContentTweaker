package com.blamejared.contenttweaker.blocks.types.machine.item.capability;

import com.blamejared.contenttweaker.blocks.types.machine.*;
import com.blamejared.contenttweaker.blocks.types.machine.capability.*;
import com.blamejared.contenttweaker.blocks.types.machine.gui.*;
import mcp.*;
import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.*;
import net.minecraftforge.items.*;

import javax.annotation.*;


@MethodsReturnNonnullByDefault
public class ItemCapabilityInstance implements ICotCapabilityInstanceTickable {
    
    @CapabilityInject(IItemHandler.class)
    static Capability<IItemHandler> ITEM_HANDLER_CAPABILITY = null;
    
    private final CoTItemStackHandler handler;
    private final ItemSlotList playerSlotItemList;
    private final boolean needsTicking;
    private byte tickCooldown = 8;
    
    @ParametersAreNonnullByDefault
    public ItemCapabilityInstance(CoTCapabilityInstanceManager manager, ItemSlotList slotList, ItemSlotList playerSlotItemList, CompoundNBT nbt) {
        this.handler = new CoTItemStackHandler(manager, slotList);
        this.handler.deserializeNBT(nbt);
        this.playerSlotItemList = playerSlotItemList;
        this.needsTicking = slotList.needsTicking();
    }
    
    
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == ITEM_HANDLER_CAPABILITY) {
            return handler.getItemHandlerCapability(side).cast();
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
        handler.addToContainer(coTContainer);
        playerSlotItemList.addToContainer(coTContainer, playerInventory);
    }
    
    @Override
    public boolean tick(CoTTileTicking tile) {
        if(tickCooldown-- > 1){
            return false;
        }
        tickCooldown = 8;
        return handler.tick(tile);
    }
    
    @Override
    public boolean needsTicking() {
        return needsTicking;
    }
}
