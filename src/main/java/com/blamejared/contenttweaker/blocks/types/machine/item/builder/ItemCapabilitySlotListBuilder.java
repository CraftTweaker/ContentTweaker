package com.blamejared.contenttweaker.blocks.types.machine.item.builder;

import com.blamejared.contenttweaker.blocks.types.machine.item.builder.*;
import com.blamejared.contenttweaker.blocks.types.machine.item.capability.*;
import com.blamejared.contenttweaker.zencode.*;

import java.util.*;
import java.util.function.*;

public class ItemCapabilitySlotListBuilder {
    
    private final NavigableMap<Integer, ItemCapabilitySlotBuilder> slotList = new TreeMap<>();
    
    public ItemCapabilitySlotListBuilder() {
    }
    
    public ItemCapabilitySlotListBuilder(int[] slots) {
        for(int slot : slots) {
            slotList.put(slot, new ItemCapabilitySlotBuilder(slot));
        }
    }
    
    public ItemCapabilitySlotListBuilder(CoTIntRange... numbers) {
        Arrays.stream(numbers)
                .flatMapToInt(CoTIntRange::stream)
                .distinct()
                .sorted()
                .forEach(s -> slotList.put(s, new ItemCapabilitySlotBuilder(s)));
    }
    
    public void forEachSlot(Consumer<ItemCapabilitySlotBuilder> consumer) {
        slotList.values().forEach(consumer);
    }
    
    public void forSlots(SlotSelector slots, Consumer<ItemCapabilitySlotBuilder> consumer) {
        for(ItemCapabilitySlotBuilder slot : slotList.values()) {
            if(slots.matches(slot)) {
                consumer.accept(slot);
            }
        }
    }
    
    public ItemSlotList buildItemSlotList() {
        final Map<Integer, ItemSlot> itemSlotList = new HashMap<>();
        int actualIndex = 0;
        for(int index : slotList.navigableKeySet()) {
            ItemCapabilitySlotBuilder builder = slotList.get(index);
            itemSlotList.put(index, builder.buildItemSlot(actualIndex));
            actualIndex++;
        }
    
    
        return new ItemSlotList(itemSlotList);
    }
}
