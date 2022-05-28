package com.blamejared.contenttweaker.forge.zen.rt;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.forge.api.zen.ContentTweakerForgeConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraftforge.common.TierSortingRegistry;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;

@ZenCodeType.Name(TierMetaFactory.ZEN_NAME)
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class TierMetaFactory {
    public static final String ZEN_NAME = ContentTweakerForgeConstants.FORGE_RT_PACKAGE + ".ToolTierMetaFactory";

    @ZenCodeType.Method("factory")
    public static Tier factory(final ResourceLocation name, final boolean marker) {
        final Tier tier = TierSortingRegistry.byName(Objects.requireNonNull(name));
        if (marker && Objects.isNull(tier)) {
            throw new IllegalStateException("No such tier with name '" + name + "' is known: maybe the mod did not register it?");
        }
        return tier;
    }
}
