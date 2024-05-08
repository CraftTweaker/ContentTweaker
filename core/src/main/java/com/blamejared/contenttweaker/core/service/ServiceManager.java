package com.blamejared.contenttweaker.core.service;

import com.blamejared.contenttweaker.core.api.util.ServiceArchitect;

import java.util.function.Supplier;

public final class ServiceManager {
    private static final Supplier<PlatformService> PLATFORM = ServiceArchitect.find(PlatformService.class);

    public static PlatformService platform() {
        return PLATFORM.get();
    }
}
