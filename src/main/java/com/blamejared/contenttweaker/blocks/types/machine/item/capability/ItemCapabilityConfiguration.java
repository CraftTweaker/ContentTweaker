package com.blamejared.contenttweaker.blocks.types.machine.item.capability;

import com.blamejared.contenttweaker.api.resources.*;
import com.blamejared.contenttweaker.blocks.types.machine.capability.*;
import com.blamejared.contenttweaker.blocks.types.machine.gui.*;
import com.blamejared.crafttweaker.impl.util.*;
import net.minecraft.nbt.*;

import javax.annotation.*;
import java.util.*;

public class ItemCapabilityConfiguration implements ICoTCapabilityConfiguration {
    
    private final ItemSlotList slotList;
    private final ItemSlotList playerSlotItemList;
    
    public ItemCapabilityConfiguration(ItemSlotList slotList, ItemSlotList playerSlotItemList) {
        this.slotList = slotList;
        this.playerSlotItemList = playerSlotItemList;
    }
    
    @Override
    @Nullable
    public ItemCapabilityInstance createCapabilityInstance(CoTCapabilityInstanceManager instanceManager) {
        CompoundNBT nbt = slotList.createDefaultNbt();
        return new ItemCapabilityInstance(instanceManager, slotList, playerSlotItemList, nbt);
    }
    
    @Override
    public void addToScreen(CoTScreen screen) {
    }
    
    @Override
    public Collection<WriteableResource> getResourcePackResources(MCResourceLocation blockName) {
        return Collections.emptyList();
    }
    
}
