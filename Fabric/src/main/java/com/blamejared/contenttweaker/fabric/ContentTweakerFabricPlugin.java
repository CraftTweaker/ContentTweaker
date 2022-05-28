package com.blamejared.contenttweaker.fabric;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.plugin.ContentTweakerPlugin;
import com.blamejared.contenttweaker.core.api.plugin.ContentTweakerPluginProvider;
import com.blamejared.contenttweaker.core.api.plugin.CustomBracketRegistration;
import com.blamejared.contenttweaker.fabric.zen.bracket.ContentTweakerFabricBrackets;

@ContentTweakerPlugin(ContentTweakerConstants.MOD_ID + ":fabric")
public class ContentTweakerFabricPlugin implements ContentTweakerPluginProvider {
    @Override
    public void registerCustomBrackets(final CustomBracketRegistration registration) {
        ContentTweakerFabricBrackets.registerBrackets(registration);
    }
}
