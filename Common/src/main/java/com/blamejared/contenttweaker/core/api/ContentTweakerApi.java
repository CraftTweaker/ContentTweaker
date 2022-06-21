package com.blamejared.contenttweaker.core.api;

import com.blamejared.contenttweaker.core.api.action.ContentTweakerAction;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.google.common.base.Suppliers;

import java.util.ServiceLoader;
import java.util.function.Supplier;

public final class ContentTweakerApi {
    private static final Supplier<ApiBridge> BRIDGE = Suppliers.memoize(ContentTweakerApi::find);

    private ContentTweakerApi() {}

    public static ApiBridge get() {
        return BRIDGE.get();
    }

    public static void apply(final ContentTweakerAction action) {
        CraftTweakerAPI.apply(action);
    }

    private static ApiBridge find() {
        return ServiceLoader.load(ApiBridge.class).findFirst().orElseThrow();
    }

}
