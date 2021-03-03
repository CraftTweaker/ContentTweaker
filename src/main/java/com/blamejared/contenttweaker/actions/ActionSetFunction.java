package com.blamejared.contenttweaker.actions;

import com.blamejared.contenttweaker.VanillaFactory;
import com.blamejared.contenttweaker.api.IHasResourceLocation;
import com.blamejared.contenttweaker.api.functions.ICotFunction;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.actions.IUndoableAction;

public class ActionSetFunction<T extends ICotFunction> implements IUndoableAction {
    private final T function;
    private final Class<T> functionType;
    private final IHasResourceLocation hasResourceLocation;

    public ActionSetFunction(T function, Class<T> functionType, IHasResourceLocation hasResourceLocation) {
        this.function = function;
        this.functionType = functionType;
        this.hasResourceLocation = hasResourceLocation;
    }

    public static <T extends ICotFunction> void applyNewAction(T function, Class<T> functionType, IHasResourceLocation hasResourceLocation) {
        CraftTweakerAPI.apply(new ActionSetFunction<>(function, functionType, hasResourceLocation));
    }

    @Override
    public void apply() {
        VanillaFactory.REGISTRY.putFunction(hasResourceLocation, function, functionType);
    }

    @Override
    public String describe() {
        return "Setting " + functionType.getTypeName() + " to CoT registry entry: " + hasResourceLocation.getRegistryName();
    }

    @Override
    public void undo() {
        VanillaFactory.REGISTRY.removeFunction(hasResourceLocation, functionType);
    }

    @Override
    public String describeUndo() {
        return "Undoing " + functionType.getTypeName() + " to CoT registry entry: " + hasResourceLocation.getRegistryName();
    }
}
