package com.blamejared.contenttweaker.vanilla.api;

import com.blamejared.contenttweaker.core.api.util.ServiceArchitect;

import java.util.function.Supplier;

public final class ContentTweakerVanillaApi {
    private static final Supplier<VanillaBridge> BRIDGE = ServiceArchitect.find(VanillaBridge.class);

    private ContentTweakerVanillaApi() {}

    public static VanillaBridge get() {
        return BRIDGE.get();
    }
}
