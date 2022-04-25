package com.blamejared.contenttweaker.core.service;

import com.google.common.base.Suppliers;

import java.util.ServiceLoader;
import java.util.function.Supplier;

public final class ServiceManager {
    private static final Supplier<PlatformService> PLATFORM = Suppliers.memoize(() -> lookup(PlatformService.class));

    public static PlatformService platform() {
        return PLATFORM.get();
    }

    private static <T> T lookup(final Class<T> serviceClass) {
        return ServiceLoader.load(serviceClass).findFirst().orElseThrow();
    }
}
