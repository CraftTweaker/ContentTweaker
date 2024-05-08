package com.blamejared.contenttweaker.core.api;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.google.common.base.Suppliers;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public final class ContentTweakerLoggers {
    private static final Supplier<Logger> CORE = logger("Core");
    private static final Supplier<Logger> PLUGIN = logger("Plugin");
    private static final Supplier<Logger> RESOURCES = logger("Resources");
    private static final Supplier<Logger> USER = logger("UserScripts");

    private ContentTweakerLoggers() {}

    public static Logger core() {
        return CORE.get();
    }

    public static Logger plugin() {
        return PLUGIN.get();
    }

    public static Logger resources() {
        return RESOURCES.get();
    }

    public static Logger user() {
        return USER.get();
    }

    private static Supplier<Logger> logger(final String subSystem) {
        return Suppliers.memoize(() -> CraftTweakerAPI.getLogger(ContentTweakerConstants.MOD_NAME + '-' + subSystem));
    }
}
