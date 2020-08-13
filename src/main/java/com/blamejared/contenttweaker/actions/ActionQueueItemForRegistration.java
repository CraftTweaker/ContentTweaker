package com.blamejared.contenttweaker.actions;

import com.blamejared.contenttweaker.*;
import com.blamejared.contenttweaker.api.*;
import com.blamejared.contenttweaker.api.items.*;
import com.blamejared.crafttweaker.api.actions.*;
import com.blamejared.crafttweaker.api.logger.*;
import net.minecraftforge.fml.*;

public class ActionQueueItemForRegistration implements IAction {
    
    private final IIsCotItem item;
    private final CoTRegistry registry;
    
    public ActionQueueItemForRegistration(IIsCotItem item, CoTRegistry registry) {
        this.item = item;
        this.registry = registry;
    }
    
    @Override
    public void apply() {
        registry.addItem(item);
    }
    
    @Override
    public String describe() {
        return String.format("Queueing Item '%s' for registration", item.getMCResourceLocation().getInternal());
    }
    
    @Override
    public boolean validate(ILogger logger) {
        if(VanillaFactory.isRegisterAllowed()) {
            return true;
        }
        
        logger.warning(String.format("Cannot register Item '%s' since it was called too late. Registering must be done during '#loader contenttweaker'!", item
                .getMCResourceLocation()
                .getInternal()));
        return false;
    }
    
    @Override
    public boolean shouldApplyOn(LogicalSide side) {
        return true;
    }
}
