package com.blamejared.contenttweaker.actions;

import com.blamejared.contenttweaker.api.IHasResourceLocation;
import com.blamejared.contenttweaker.api.functions.ICotFunction;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import net.minecraftforge.fml.LogicalSide;

public class ActionSetFunctionClient<T extends ICotFunction> extends ActionSetFunction<T> {
    public ActionSetFunctionClient(T function, Class<T> functionType, IHasResourceLocation hasResourceLocation) {
        super(function, functionType, hasResourceLocation);
    }

    public static <T extends ICotFunction> void applyNewAction(T function, Class<T> functionType, IHasResourceLocation hasResourceLocation) {
        CraftTweakerAPI.apply(new ActionSetFunctionClient<>(function, functionType, hasResourceLocation));
    }

    @Override
    public boolean shouldApplyOn(LogicalSide side) {
        return side.isClient();
    }
}
