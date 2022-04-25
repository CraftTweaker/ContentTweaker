package com.blamejared.contenttweaker.forge;

import com.blamejared.contenttweaker.core.ContentTweakerCore;
import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import net.minecraftforge.fml.common.Mod;

@Mod(ContentTweakerConstants.MOD_ID)
public final class ContentTweaker {

    public ContentTweaker() {
        ContentTweakerCore.core().initialize();
    }

}
