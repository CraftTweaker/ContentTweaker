package com.blamejared.contenttweaker.blocks.types.machine.item.builder;

import com.blamejared.contenttweaker.blocks.types.machine.*;
import com.blamejared.contenttweaker.blocks.types.machine.capability.*;
import com.blamejared.contenttweaker.blocks.types.machine.item.capability.*;
import com.blamejared.contenttweaker.zencode.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.impl.util.*;
import org.openzen.zencode.java.*;

import java.util.function.*;

@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.block.machine.ItemCapabilityBuilder")
public class ItemCapabilityBuilder implements IIsCoTCapabilityBuilder {
    
    private ItemCapabilitySlotListBuilder slots;
    
    public ItemCapabilityBuilder() {
        this.slots = new ItemCapabilitySlotListBuilder();
    }
    
    
    
    @ZenCodeType.Method
    public ItemCapabilityBuilder withSlotNumbers(CoTIntRange... numbers) {
        slots = new ItemCapabilitySlotListBuilder(numbers);
        return this;
    }

    
    @ZenCodeType.Method
    public ItemCapabilityBuilder forAllSlots(Consumer<ItemCapabilitySlotBuilder> consumer) {
        slots.forEachSlot(consumer);
        return this;
    }
    
    
    @ZenCodeType.Method
    public ItemCapabilityBuilder forSlots(SlotSelector[] slotIndices, Consumer<ItemCapabilitySlotBuilder> consumer) {
        slots.forSlots(SlotSelector.mergeOr(slotIndices), consumer);
        return this;
    }
    
    @ZenCodeType.Method
    public ItemCapabilityBuilder forSlots(SlotSelector slotIndices, Consumer<ItemCapabilitySlotBuilder> consumer) {
        slots.forSlots(slotIndices, consumer);
        return this;
    }
    
    @ZenCodeType.Method
    public ItemCapabilityBuilder groupSlots(String groupName, SlotSelector... selector){
        forSlots(selector, itemCapabilitySlot -> itemCapabilitySlot.addToGroup(groupName));
        return this;
    }
    
    @ZenCodeType.Method
    public ItemCapabilityBuilder setInputOn(MCDirection side, SlotSelector... selector) {
        forSlots(selector, itemCapabilitySlot -> itemCapabilitySlot.setInputOn(side));
        return this;
    }
    
    @ZenCodeType.Method
    public ItemCapabilityBuilder setInputOn(MCDirection[] sides, SlotSelector... selector) {
        ItemCapabilityBuilder out = this;
        for(MCDirection side : sides) {
            out = out.setInputOn(side, selector);
        }
        return out;
    }
    
    @ZenCodeType.Method
    public ItemCapabilityBuilder setOutputOn(MCDirection side, SlotSelector... selector) {
        forSlots(selector, itemCapabilitySlot -> itemCapabilitySlot.setOutputOn(side));
        return this;
    }
    
    @ZenCodeType.Method
    public ItemCapabilityBuilder setPushTo(MCDirection side, SlotSelector... selector) {
        forSlots(selector, itemCapabilitySlot -> itemCapabilitySlot.setPushTo(side));
        return this;
    }
    
    @ZenCodeType.Method
    public ItemCapabilityBuilder setPullFrom(MCDirection side, SlotSelector... selector) {
        forSlots(selector, itemCapabilitySlot -> itemCapabilitySlot.setPullFrom(side));
        return this;
    }
    
    @ZenCodeType.Method
    public ItemCapabilityBuilder hideSlots(SlotSelector... selector) {
        forSlots(selector, ItemCapabilitySlotBuilder::setHidden);
        return this;
    }
    
    @Override
    public void applyToBuilder(BuilderMachine builderMachine) {
        if(builderMachine.hasCapability(CoTCapabilities.ITEM)) {
            throw new IllegalStateException("A block can only have one item Capability!");
        }
        
        builderMachine.addCapability(CoTCapabilities.ITEM, buildCapabilityConfiguration());
    }
    
    private ItemCapabilityConfiguration buildCapabilityConfiguration() {
        return new ItemCapabilityConfiguration(slots.buildItemSlotList());
    }
}
