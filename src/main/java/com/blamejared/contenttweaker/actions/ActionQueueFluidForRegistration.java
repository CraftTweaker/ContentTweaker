package com.blamejared.contenttweaker.actions;

import com.blamejared.contenttweaker.VanillaFactory;
import com.blamejared.contenttweaker.api.CoTRegistry;
import com.blamejared.contenttweaker.api.fluids.IIsCotFluid;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.actions.IAction;
import com.blamejared.crafttweaker.api.logger.ILogger;
import com.blamejared.crafttweaker.api.zencode.impl.loaders.LoaderActions;
import org.openzen.zencode.shared.CodePosition;

public class ActionQueueFluidForRegistration implements IAction {
    private final IIsCotFluid fluid;
    private final CoTRegistry registry;

    public ActionQueueFluidForRegistration(IIsCotFluid fluid, CoTRegistry registry) {
        this.fluid = fluid;
        this.registry = registry;
    }

    @Override
    public void apply() {
        registry.addFluid(fluid);
    }

    @Override
    public boolean validate(ILogger logger) {
        if(VanillaFactory.isRegisterAllowed()) {
            return true;
        }

        final LoaderActions loaderActions = CraftTweakerAPI.getCurrentRun().getLoaderActions();
        final CodePosition declaredScriptPosition = getDeclaredScriptPosition();
        final String format = "Cannot register Fluid '%s' since it was called too late. Registering must be done during '#loader contenttweaker', but file '%s' is loaded in '#loader %s'!";
        logger.error(String.format(format, fluid.getRegistryName(), declaredScriptPosition.getFilename(), loaderActions.getLoaderName()));
        return false;
    }

    @Override
    public String describe() {
        return "Registering fluid: " + fluid.getRegistryName();
    }
}
