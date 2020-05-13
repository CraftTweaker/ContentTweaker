package com.blamejared.contenttweaker.blocks.types.machine.item.capability;

import com.blamejared.contenttweaker.blocks.types.machine.*;
import com.blamejared.contenttweaker.blocks.types.machine.gui.*;
import com.blamejared.crafttweaker.impl.item.*;
import com.blamejared.crafttweaker.impl.util.*;
import it.unimi.dsi.fastutil.ints.*;
import net.minecraft.inventory.*;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.*;
import net.minecraftforge.items.*;

import java.util.*;

public class ItemSlotList {
    
    @CapabilityInject(IItemHandler.class)
    static Capability<IItemHandler> ITEM_HANDLER_CAPABILITY = null;
    
    private final Map<Integer, ItemSlot> slotMap;
    private final Int2IntArrayMap slotMapping;
    
    public ItemSlotList(Map<Integer, ItemSlot> slotMap) {
        this.slotMap = slotMap;
        this.slotMapping = new Int2IntArrayMap(slotMap.size());
        slotMap.forEach((integer, itemSlot) -> slotMapping.put(itemSlot.getActualIndex(), integer.intValue()));
    }
    
    //public ItemSlot getSpecifiedSlot(int index) {
    //    return slotMap.get(index);
    //}
    
    public Collection<MCDirection> getInputDirections() {
        final Set<MCDirection> mcDirections = new HashSet<>();
        for(ItemSlot value : slotMap.values()) {
            value.addInputSidesTo(mcDirections);
        }
        return mcDirections;
    }
    
    public CompoundNBT createDefaultNbt() {
        return new ItemStackHandler(slotMap.size()).serializeNBT();
    }
    
    public void addToContainer(CoTContainer container, IInventory handler) {
        for(ItemSlot value : slotMap.values()) {
            value.addToContainer(container, handler);
        }
    }
    
    public int[] getSlotsForFace(MCDirection direction) {
        IntSet arraySet = new IntRBTreeSet();
        for(ItemSlot value : slotMap.values()) {
            if(value.isVisibleFrom(direction)) {
                arraySet.add(value.getActualIndex());
            }
        }
        return arraySet.toIntArray();
    }
    
    public boolean canInsertItem(int index, MCItemStackMutable mcItemStackMutable, MCDirection direction) {
        final ItemSlot slotByActualSlot = findSlotByActualSlot(index);
        if(!slotByActualSlot.inputsFrom(direction)) {
            return false;
        }
        return slotByActualSlot.acceptsItemInput(mcItemStackMutable);
    }
    
    public boolean canExtractItem(int index, MCItemStackMutable mcItemStackMutable, MCDirection direction) {
        final ItemSlot slotByActualSlot = findSlotByActualSlot(index);
        if(!slotByActualSlot.outputsTo(direction)) {
            return false;
        }
        return slotByActualSlot.canOutput(mcItemStackMutable);
    }
    
    private ItemSlot findSlotByActualSlot(int index) {
        return slotMap.get(slotMapping.get(index));
    }
    
    public boolean needsTicking() {
        for(ItemSlot value : slotMap.values()) {
            if(value.needsTicking()) {
                return true;
            }
        }
        return false;
    }
    
    public boolean tick(CoTTileTicking tile, ItemHandlerInventory itemHandlerInventory) {
        final BlockPos pos = tile.getPos();
        final World world = tile.getWorld();
        if(world == null) {
            return false;
        }
        
        final Set<MCDirection> activeSides = new HashSet<>();
        for(ItemSlot value : slotMap.values()) {
            value.fillActiveSides(activeSides);
        }
        
        final Map<MCDirection, IItemHandler> activeSidesHandlers = new HashMap<>();
        for(MCDirection activeSide : activeSides) {
            final Direction sideInternal = activeSide.getInternal();
            final BlockPos offset = pos.offset(sideInternal);
            final TileEntity tileEntity = world.getTileEntity(offset);
            if(tileEntity == null) {
                continue;
            }
            final Direction opposite = sideInternal.getOpposite();
            LazyOptional<IItemHandler> capability = tileEntity.getCapability(ITEM_HANDLER_CAPABILITY, opposite);
            if(!capability.isPresent()) {
                capability = tileEntity.getCapability(ITEM_HANDLER_CAPABILITY);
            }
            capability.ifPresent(itemHandler -> {
                activeSidesHandlers.put(activeSide, itemHandler);
            });
        }
        
        boolean dirty = false;
        for(ItemSlot value : slotMap.values()) {
            if(value.needsTicking()) {
                for(Map.Entry<MCDirection, IItemHandler> entry : activeSidesHandlers.entrySet()) {
                    MCDirection direction = entry.getKey();
                    IItemHandler itemHandler = entry.getValue();
                    if(value.tick(itemHandlerInventory, direction, itemHandler)) {
                        dirty = true;
                    }
                }
            }
        }
        return dirty;
    }
    
    public int getMaxStackSize(int index) {
        return findSlotByActualSlot(index).getMaxStackSize();
    }
}
