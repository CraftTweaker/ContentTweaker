package com.blamejared.contenttweaker.fabric;

import com.blamejared.contenttweaker.core.ContentTweakerCore;
import net.fabricmc.api.ModInitializer;

public final class ContentTweaker implements ModInitializer {

    @Override
    public void onInitialize() {
        ContentTweakerCore.core().initialize();
    }
}
