package com.blamejared.contenttweaker.actions;

import com.blamejared.contenttweaker.*;
import com.blamejared.contenttweaker.api.*;
import com.blamejared.contenttweaker.api.blocks.*;
import com.blamejared.crafttweaker.api.actions.*;
import com.blamejared.crafttweaker.api.logger.*;
import net.minecraftforge.fml.*;

/**
 * Enqueues an item for registration
 */
public class ActionQueueBlockForRegistration implements IAction {
    
    private final IIsCoTBlock block;
    private final CoTRegistry registry;
    
    public ActionQueueBlockForRegistration(IIsCoTBlock block, CoTRegistry registry) {
        this.block = block;
        this.registry = registry;
    }
    
    @Override
    public void apply() {
        registry.addBlock(block);
        registry.addItem(block.getItem());
    }
    
    @Override
    public String describe() {
        return String.format("Queueing Block '%s' for registration.", block.getMCResourceLocation().getInternal());
    }
    
    @Override
    public boolean validate(ILogger logger) {
        if(VanillaFactory.isRegisterAllowed()) {
            return true;
        }
        logger.warning(String.format("Cannot register block '%s' since it was called too late. Registering must be done during '#loader contenttweaker'!", block
                .getMCResourceLocation()
                .getInternal()));
        return false;
    }
    
    @Override
    public boolean shouldApplyOn(LogicalSide side) {
        return true;
    }
}
