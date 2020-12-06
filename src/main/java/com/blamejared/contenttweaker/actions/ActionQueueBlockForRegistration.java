package com.blamejared.contenttweaker.actions;

import com.blamejared.contenttweaker.*;
import com.blamejared.contenttweaker.api.*;
import com.blamejared.contenttweaker.api.blocks.*;
import com.blamejared.crafttweaker.api.*;
import com.blamejared.crafttweaker.api.actions.*;
import com.blamejared.crafttweaker.api.logger.*;
import com.blamejared.crafttweaker.api.zencode.impl.loaders.*;
import net.minecraftforge.fml.*;
import org.openzen.zencode.shared.*;

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
        
        final LoaderActions loaderActions = CraftTweakerAPI.getCurrentRun().getLoaderActions();
        final CodePosition declaredScriptPosition = getDeclaredScriptPosition();
        final String format = "Cannot register block '%s' since it was called too late. Registering must be done during '#loader contenttweaker', but file %s is loaded in '#loader %s'!";
        logger.error(String.format(format, block.getMCResourceLocation().getInternal(), declaredScriptPosition.getFilename(), loaderActions.getLoaderName()));
        return false;
    }
    
    @Override
    public boolean shouldApplyOn(LogicalSide side) {
        return true;
    }
}
