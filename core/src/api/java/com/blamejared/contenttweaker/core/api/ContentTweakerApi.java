package com.blamejared.contenttweaker.core.api;

import com.blamejared.contenttweaker.core.api.action.ContentTweakerAction;
import com.blamejared.contenttweaker.core.api.util.ServiceArchitect;

import java.util.function.Supplier;

public final class ContentTweakerApi {
    private static final Supplier<ApiBridge> BRIDGE = ServiceArchitect.find(ApiBridge.class);

    private ContentTweakerApi() {}

    public static ApiBridge get() {
        return BRIDGE.get();
    }

    public static void apply(final ContentTweakerAction action) {
        get().apply(action);
    }

}
