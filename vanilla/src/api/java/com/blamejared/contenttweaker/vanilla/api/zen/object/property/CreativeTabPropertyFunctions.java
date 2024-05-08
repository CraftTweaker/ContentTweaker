package com.blamejared.contenttweaker.vanilla.api.zen.object.property;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.object.ItemReference;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.world.flag.FeatureFlagSet;
import org.openzen.zencode.java.ZenCodeType;

public final class CreativeTabPropertyFunctions {
    @FunctionalInterface
    @ZenCodeType.Name(ContentTweakerVanillaConstants.VANILLA_OBJECT_PACKAGE + ".property.fun.DisplayItemsGatherer")
    @ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
    public interface DisplayItemsGatherer {
        // TODO("Exposing provider doesn't make much sense IMO")
        void gather(final FeatureFlagSet set, final boolean hasPermission, final Object provider, final DisplayItemsGatheringStream stream);
    }

    @FunctionalInterface
    @ZenCodeType.Name(ContentTweakerVanillaConstants.VANILLA_OBJECT_PACKAGE + ".property.fun.DisplayItemsGatheringStream")
    @ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
    public interface DisplayItemsGatheringStream {
        void accept(
                final ItemReference item,
                @ZenCodeType.Optional("<constant:minecraft:tab/visibility:parent_and_search>") final CreativeTabProperties.ItemTabVisibility visibility
        );
    }

    private CreativeTabPropertyFunctions() {}
}
