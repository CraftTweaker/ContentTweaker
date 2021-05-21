package com.blamejared.contenttweaker.actions;

import com.blamejared.contenttweaker.VanillaFactory;
import com.blamejared.contenttweaker.api.CoTRegistry;
import com.blamejared.contenttweaker.api.blocks.IIsCoTBlock;
import com.blamejared.contenttweaker.blocks.render.BlockRenderType;
import com.blamejared.contenttweaker.blocks.render.BlockRenderTypeCollection;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.actions.IAction;
import com.blamejared.crafttweaker.api.logger.ILogger;
import com.blamejared.crafttweaker.api.zencode.impl.loaders.LoaderActions;
import net.minecraftforge.fml.LogicalSide;
import org.openzen.zencode.shared.CodePosition;

/**
 * Enqueues an item for registration
 */
public class ActionQueueBlockForRegistration implements IAction {
    
    private final IIsCoTBlock block;
    private final CoTRegistry registry;
    private final BlockRenderType renderType;
    
    public ActionQueueBlockForRegistration(IIsCoTBlock block, CoTRegistry registry, BlockRenderType renderType) {
        this.block = block;
        this.registry = registry;
        this.renderType = renderType;
    }
    
    @Override
    public void apply() {
        registry.addBlock(block);
        registry.addItem(block.getItem());
        BlockRenderTypeCollection.addRenderTypeRule(block, renderType);
    }
    
    @Override
    public String describe() {
        return String.format("Queueing Block '%s' for registration.", block.getRegistryName());
    }
    
    @Override
    public boolean validate(ILogger logger) {
        if(VanillaFactory.isRegisterAllowed()) {
            return true;
        }
        
        final LoaderActions loaderActions = CraftTweakerAPI.getCurrentRun().getLoaderActions();
        final CodePosition declaredScriptPosition = getDeclaredScriptPosition();
        final String format = "Cannot register block '%s' since it was called too late. Registering must be done during '#loader contenttweaker', but file %s is loaded in '#loader %s'!";
        logger.error(String.format(format, block.getRegistryName(), declaredScriptPosition.getFilename(), loaderActions.getLoaderName()));
        return false;
    }
    
    @Override
    public boolean shouldApplyOn(LogicalSide side) {
        return true;
    }
}
