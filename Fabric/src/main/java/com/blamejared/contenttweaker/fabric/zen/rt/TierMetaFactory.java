package com.blamejared.contenttweaker.fabric.zen.rt;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.fabric.api.zen.ContentTweakerFabricConstants;
import com.blamejared.contenttweaker.fabric.util.ContentTweakerTierRegistry;
import com.blamejared.contenttweaker.vanilla.api.zen.util.TierReference;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;

@ZenCodeType.Name(TierMetaFactory.ZEN_NAME)
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class TierMetaFactory {// TODO("Fabric support in general: there is no centralized API for this")
    public static final String ZEN_NAME = ContentTweakerFabricConstants.FABRIC_RT_PACKAGE + ".ToolTierMetaFactory";

    @ZenCodeType.Method("factory")
    public static TierReference factory(final ResourceLocation name) {
        final Tier tier = ContentTweakerTierRegistry.of().find(Objects.requireNonNull(name));
        return TierReference.of(name, tier.getLevel(), () -> tier);
    }

    @ZenCodeType.Method("factory")
    public static TierReference factory(final int level) {
        final Tier tier = ContentTweakerTierRegistry.of().find(level);
        final ResourceLocation name = ContentTweakerTierRegistry.of().nameOf(tier);
        return TierReference.of(name, level, () -> tier);
    }
}
