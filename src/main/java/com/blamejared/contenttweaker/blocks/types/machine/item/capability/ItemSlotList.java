package com.blamejared.contenttweaker.blocks.types.machine.item.capability;

import com.blamejared.crafttweaker.impl.util.*;
import it.unimi.dsi.fastutil.ints.*;
import net.minecraft.nbt.*;
import net.minecraftforge.items.*;

import java.util.*;

public class ItemSlotList {
    private final Map<Integer, ItemSlot> slotMap;
    private final Int2IntMap slotMapping;
    
    public ItemSlotList(Map<Integer, ItemSlot> slotMap) {
        this.slotMap = slotMap;
        this.slotMapping = new Int2IntArrayMap();
    
        final TreeSet<Integer> integers = new TreeSet<>(slotMap.keySet());
    
        int i = 0;
        for(int specifiedSlot : integers) {
            slotMapping.put(i++, specifiedSlot);
        }
    }
    
    public ItemSlot getSpecifiedSlot(int index) {
        return slotMap.get(index);
    }
    
    public ItemSlot getContainerSlot(int containerIndex) {
        final int specifiedSlot = mapContainerIndexToSpecifiedSlot(containerIndex);
        return getSpecifiedSlot(specifiedSlot);
    }
    
    public int mapContainerIndexToSpecifiedSlot(int containerIndex){
        return slotMapping.get(containerIndex);
    }
    
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
}
