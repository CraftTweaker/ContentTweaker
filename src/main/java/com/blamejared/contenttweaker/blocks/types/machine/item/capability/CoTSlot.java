package com.blamejared.contenttweaker.blocks.types.machine.item.capability;

import com.blamejared.contenttweaker.blocks.types.machine.item.builder.*;
import com.blamejared.contenttweaker.blocks.types.machine.item.gui.*;
import com.blamejared.crafttweaker.impl.item.*;
import mcp.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.inventory.container.*;
import net.minecraft.item.*;
import net.minecraftforge.api.distmarker.*;

import javax.annotation.*;
import java.util.*;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public final class CoTSlot extends Slot {
    
    private final ItemSlotGuiInformation guiInformation;
    private final ItemSlotItemRestriction itemRestriction;
    
    public CoTSlot(IInventory inventoryIn, int index, ItemSlotGuiInformation guiInformation, ItemSlotItemRestriction itemRestriction) {
        super(inventoryIn, index, guiInformation.getPosition().x, guiInformation.getPosition().y);
        this.guiInformation = guiInformation;
        this.itemRestriction = itemRestriction;
    }
    
    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean isEnabled() {
        return guiInformation.isVisible();
    }
    
    @Override
    public boolean canTakeStack(@Nonnull PlayerEntity playerIn) {
        return guiInformation.isVisible();
    }
    
    public boolean canAccept(ItemStack clickedSlack) {
        return itemRestriction.acceptsItemInput(new MCItemStackMutable(clickedSlack));
    }
    
    @Override
    public boolean isItemValid(ItemStack stack) {
        return itemRestriction.acceptsItemInput(new MCItemStackMutable(stack));
    }
}
