package com.blamejared.contenttweaker.fabric.zen.rt;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.fabric.api.zen.ContentTweakerFabricConstants;
import com.blamejared.contenttweaker.fabric.util.ContentTweakerTierRegistry;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;

@ZenCodeType.Name(TierMetaFactory.ZEN_NAME)
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class TierMetaFactory {
    public static final String ZEN_NAME = ContentTweakerFabricConstants.FABRIC_RT_PACKAGE + ".ToolTierMetaFactory";

    @ZenCodeType.Method("factory")
    public static Tier factory(final ResourceLocation name) {
        return ContentTweakerTierRegistry.of().find(Objects.requireNonNull(name));
    }

    @ZenCodeType.Method("factory")
    public static Tier factory(final int level) {
        return ContentTweakerTierRegistry.of().find(level);
    }
}
