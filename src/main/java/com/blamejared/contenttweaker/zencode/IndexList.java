package com.blamejared.contenttweaker.zencode;

import com.blamejared.contenttweaker.blocks.types.machine.item.builder.*;
import it.unimi.dsi.fastutil.ints.*;

import java.util.*;

public class IndexList implements SlotSelector {
    
    final IntSet indices;
    
    public IndexList(int... indices) {
        this.indices = new IntArraySet(indices);
    }
    
    public IndexList(CoTIntRange... ranges) {
        final int[] indices = Arrays.stream(ranges)
                .flatMapToInt(CoTIntRange::stream)
                .distinct()
                .sorted()
                .toArray();
        
        this.indices = new IntArraySet(indices);
    }
    
    @Override
    public boolean matches(ItemCapabilitySlotBuilder slot) {
        return indices.contains(slot.getSpecifiedIndex());
    }
}
