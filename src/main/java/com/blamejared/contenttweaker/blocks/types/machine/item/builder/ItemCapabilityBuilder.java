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
    private ItemCapabilitySlotListBuilder playerInventorySlots;
    
    public ItemCapabilityBuilder() {
        this.slots = new ItemCapabilitySlotListBuilder();
        this.playerInventorySlots = new ItemCapabilitySlotListBuilder();
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
    public ItemCapabilityBuilder groupSlots(String groupName, SlotSelector... selector) {
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
    
    @ZenCodeType.Method
    public ItemCapabilityBuilder addPlayerInventory() {
        final CoTIntRange range = new CoTIntRange(0, 36);
        this.playerInventorySlots = new ItemCapabilitySlotListBuilder(range);
        return this;
    }
    
    @ZenCodeType.Method
    public ItemCapabilityBuilder forPlayerSlots(SlotSelector selector, Consumer<ItemCapabilitySlotBuilder> consumer) {
        playerInventorySlots.forSlots(selector, consumer);
        return this;
    }
    
    @ZenCodeType.Method
    public ItemCapabilityBuilder forPlayerInventorySlots(Consumer<ItemCapabilitySlotBuilder> consumer) {
        final CoTIntRange range = new CoTIntRange(9, 36);
        return forPlayerSlots(new IndexList(range), consumer);
    }
    
    @ZenCodeType.Method
    public ItemCapabilityBuilder forPlayerHotbarSlots(Consumer<ItemCapabilitySlotBuilder> consumer) {
        final CoTIntRange range = new CoTIntRange(0, 9);
        return forPlayerSlots(new IndexList(range), consumer);
    }
    
    @Override
    public void applyToBuilder(BuilderMachine builderMachine) {
        if(builderMachine.hasCapability(CoTCapabilities.ITEM)) {
            throw new IllegalStateException("A block can only have one item Capability!");
        }
        
        builderMachine.addCapability(CoTCapabilities.ITEM, buildCapabilityConfiguration());
    }
    
    private ItemCapabilityConfiguration buildCapabilityConfiguration() {
        final ItemSlotList declaredSlotItemSlotLost = slots.buildItemSlotList();
        final ItemSlotList playerSlotItemList = playerInventorySlots.buildItemSlotList();
        return new ItemCapabilityConfiguration(declaredSlotItemSlotLost, playerSlotItemList);
    }
}
