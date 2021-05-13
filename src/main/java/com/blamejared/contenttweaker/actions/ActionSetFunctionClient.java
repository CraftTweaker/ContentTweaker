package com.blamejared.contenttweaker.actions;

import com.blamejared.contenttweaker.api.IHasResourceLocation;
import com.blamejared.contenttweaker.api.functions.ICotFunction;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import net.minecraftforge.fml.LogicalSide;

import java.util.function.BiConsumer;

public class ActionSetFunctionClient<T extends IHasResourceLocation, F extends ICotFunction> extends ActionSetFunction<T, F> {

    public ActionSetFunctionClient(String type, T target, F function, BiConsumer<T, F> setFunctionConsumer) {
        super(type, target, function, setFunctionConsumer);
    }

    public ActionSetFunctionClient(String type, T target, F function, F defaultFunction, BiConsumer<T, F> setFunctionConsumer) {
        super(type, target, function, defaultFunction, setFunctionConsumer);
    }

    public static <T extends IHasResourceLocation, F extends ICotFunction> void applyNewAction(String type, T target, F function, BiConsumer<T, F> setFunctionConsumer) {
        CraftTweakerAPI.apply(new ActionSetFunctionClient<>(type, target, function, setFunctionConsumer));
    }

    public static <T extends IHasResourceLocation, F extends ICotFunction> void applyNewAction(String type, T target, F function, F defaultFunction, BiConsumer<T, F> setFunctionConsumer) {
        CraftTweakerAPI.apply(new ActionSetFunctionClient<>(type, target, function, defaultFunction, setFunctionConsumer));
    }

    @Override
    public boolean shouldApplyOn(LogicalSide side) {
        return side.isClient();
    }
}
