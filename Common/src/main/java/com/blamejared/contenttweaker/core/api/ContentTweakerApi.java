package com.blamejared.contenttweaker.core.api;

import com.blamejared.contenttweaker.core.api.action.ContentTweakerAction;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.google.common.base.Suppliers;

import java.util.Objects;
import java.util.ServiceLoader;
import java.util.function.Supplier;

public final class ContentTweakerApi {
    private static final Supplier<ApiBridge> BRIDGE = Suppliers.memoize(ContentTweakerApi::find);

    private ContentTweakerApi() {}

    public static ApiBridge get() {
        return BRIDGE.get();
    }

    public static void apply(final ContentTweakerAction action) {
        try {
            CraftTweakerAPI.apply(action);
        } catch (final UnsupportedOperationException e) {
            if (e.getMessage() == null || !e.getMessage().contains("outside of a script run")) {
                throw e;
            }

            // ContentTweaker does not support staged action as of now, which we require for some things like creative
            // tab editing and modification and tool tiers... essentially everything that cannot be determined at script
            // execution time. While we wait to find a proper solution in CraftTweaker, we manually log and apply the
            // action
            // TODO("Discuss this internally and find how to best approach staged actions as they are now required")
            try {
                if (!action.validate(CraftTweakerAPI.LOGGER)) {
                    return;
                }
                CraftTweakerAPI.LOGGER.info("ContentTweaker Staged: %s".formatted(Objects.requireNonNull(action.describe())));
                action.apply();
            } catch (final Exception ex) {
                CraftTweakerAPI.LOGGER.error("Unable to execute a ContentTweaker staged action due to an error", ex);
            }
        }
    }

    private static ApiBridge find() {
        return ServiceLoader.load(ApiBridge.class).findFirst().orElseThrow();
    }

}
