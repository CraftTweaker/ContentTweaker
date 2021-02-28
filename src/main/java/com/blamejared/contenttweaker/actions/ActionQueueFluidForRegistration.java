package com.blamejared.contenttweaker.actions;

import com.blamejared.contenttweaker.api.CoTRegistry;
import com.blamejared.contenttweaker.api.fluids.IIsCotFluid;
import com.blamejared.crafttweaker.api.actions.IAction;

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
    public String describe() {
        return "Registering fluid: " + fluid.getRegistryName();
    }
}
