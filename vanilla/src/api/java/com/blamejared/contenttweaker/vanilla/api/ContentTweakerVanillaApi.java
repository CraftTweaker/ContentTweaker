package com.blamejared.contenttweaker.vanilla.api;

import com.google.common.base.Suppliers;

import java.util.ServiceLoader;
import java.util.function.Supplier;

public final class ContentTweakerVanillaApi {
    private static final Supplier<VanillaBridge> BRIDGE = Suppliers.memoize(() -> ServiceLoader.load(VanillaBridge.class).findFirst().orElseThrow());

    private ContentTweakerVanillaApi() {}

    public static VanillaBridge get() {
        return BRIDGE.get();
    }
}
