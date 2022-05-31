package com.blamejared.contenttweaker.forge.zen.rt;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.forge.api.zen.ContentTweakerForgeConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.util.TierReference;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.TierSortingRegistry;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;

@ZenCodeType.Name(TierMetaFactory.ZEN_NAME)
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class TierMetaFactory {
    public static final String ZEN_NAME = ContentTweakerForgeConstants.FORGE_RT_PACKAGE + ".ToolTierMetaFactory";

    @ZenCodeType.Method("factory")
    public static TierReference factory(final ResourceLocation name) {
        Objects.requireNonNull(name);
        return TierReference.of(name, -1, () -> TierSortingRegistry.byName(name));
    }
}
