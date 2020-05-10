package com.blamejared.contenttweaker.blocks.types.machine.item.capability;

import com.blamejared.contenttweaker.blocks.types.machine.capability.*;
import net.minecraft.nbt.*;

public class ItemCapabilityConfiguration implements ICoTCapabilityConfiguration {
    private final ItemSlotList slotList;
    
    public ItemCapabilityConfiguration(ItemSlotList slotList) {
        this.slotList = slotList;
    }
    
    @Override
    public ItemCapabilityInstance createCapabilityInstance(CoTCapabilityInstanceManager instanceManager) {
        CompoundNBT nbt = slotList.createDefaultNbt();
        return new ItemCapabilityInstance(instanceManager, slotList, nbt);
    }
}
