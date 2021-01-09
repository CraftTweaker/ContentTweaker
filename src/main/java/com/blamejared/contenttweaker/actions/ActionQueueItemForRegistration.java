package com.blamejared.contenttweaker.actions;

import com.blamejared.contenttweaker.*;
import com.blamejared.contenttweaker.api.*;
import com.blamejared.contenttweaker.api.items.*;
import com.blamejared.crafttweaker.api.*;
import com.blamejared.crafttweaker.api.actions.*;
import com.blamejared.crafttweaker.api.logger.*;
import com.blamejared.crafttweaker.api.zencode.impl.loaders.*;
import net.minecraftforge.fml.*;
import org.openzen.zencode.shared.*;

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
        return String.format("Queueing Item '%s' for registration.", item.getRegistryName());
    }
    
    @Override
    public boolean validate(ILogger logger) {
        if(VanillaFactory.isRegisterAllowed()) {
            return true;
        }
        
        final LoaderActions loaderActions = CraftTweakerAPI.getCurrentRun().getLoaderActions();
        final CodePosition declaredScriptPosition = getDeclaredScriptPosition();
        final String format = "Cannot register Item '%s' since it was called too late. Registering must be done during '#loader contenttweaker', but file %s is loaded in '#loader %s'!";
        logger.error(String.format(format, item.getRegistryName(), declaredScriptPosition.getFilename(), loaderActions.getLoaderName()));
        return false;
    }
    
    @Override
    public boolean shouldApplyOn(LogicalSide side) {
        return true;
    }
}
