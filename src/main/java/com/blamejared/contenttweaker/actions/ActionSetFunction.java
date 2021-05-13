package com.blamejared.contenttweaker.actions;

import com.blamejared.contenttweaker.api.IHasResourceLocation;
import com.blamejared.contenttweaker.api.functions.ICotFunction;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import com.blamejared.crafttweaker.api.logger.ILogger;
import net.minecraftforge.fml.LogicalSide;

import java.util.function.BiConsumer;

public class ActionSetFunction<T extends IHasResourceLocation, F extends ICotFunction> implements IUndoableAction {
    private final String type;
    private final T target;
    private final F function;
    private final F defaultFunction;
    private final BiConsumer<T, F> setFunctionConsumer;

    public ActionSetFunction(String type, T target, F function, F defaultFunction, BiConsumer<T, F> setFunctionConsumer) {
        this.type = type;
        this.target = target;
        this.function = function;
        this.defaultFunction = defaultFunction;
        this.setFunctionConsumer = setFunctionConsumer;
    }

    public ActionSetFunction(String type, T target, F function, BiConsumer<T, F> setFunctionConsumer) {
        this(type, target, function, null, setFunctionConsumer);
    }

    public static <T extends IHasResourceLocation, F extends ICotFunction> void applyNewAction(String type, T target, F function, BiConsumer<T, F> setFunctionConsumer) {
        CraftTweakerAPI.apply(new ActionSetFunction<>(type, target, function, setFunctionConsumer));
    }

    public static <T extends IHasResourceLocation, F extends ICotFunction> void applyNewAction(String type, T target, F function, F defaultFunction, BiConsumer<T, F> setFunctionConsumer) {
        CraftTweakerAPI.apply(new ActionSetFunction<>(type, target, function, defaultFunction, setFunctionConsumer));
    }

    @Override
    public void apply() {
        setFunctionConsumer.accept(target, function);
    }

    @Override
    public String describe() {
        return "Setting " + type + " to CoT registry entry: " + target.getRegistryName();
    }

    @Override
    public void undo() {
        setFunctionConsumer.accept(target, defaultFunction);
    }

    @Override
    public String describeUndo() {
        return "Undoing " + type + " to CoT registry entry: " + target.getRegistryName();
    }

    @Override
    public boolean validate(ILogger logger) {
        return assertLoader(CraftTweakerAPI.getDefaultLoaderName());
    }

    @Override
    public boolean shouldApplyOn(LogicalSide side) {
        return shouldApplySingletons();
    }
}
