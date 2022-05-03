package com.blamejared.contenttweaker.core.api;

import com.google.common.base.Suppliers;

import java.util.ServiceLoader;
import java.util.function.Supplier;

public final class ContentTweakerApi {
    private static final Supplier<ApiBridge> BRIDGE = Suppliers.memoize(ContentTweakerApi::find);

    private ContentTweakerApi() {}

    public static ApiBridge get() {
        return BRIDGE.get();
    }

    private static ApiBridge find() {
        return ServiceLoader.load(ApiBridge.class).findFirst().orElseThrow();
    }

}
