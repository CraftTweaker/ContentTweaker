package com.blamejared.contenttweaker.blocks.types.machine.gui;

import com.blamejared.contenttweaker.blocks.types.machine.*;
import mcp.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.container.*;

import javax.annotation.*;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CoTContainer extends Container {
    
    
    public CoTContainer(ContainerType<CoTContainer> type, int id) {
        super(type, id);
    }
    
    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }
    
    @Override
    public Slot addSlot(Slot slotIn) {
        return super.addSlot(slotIn);
    }
}
