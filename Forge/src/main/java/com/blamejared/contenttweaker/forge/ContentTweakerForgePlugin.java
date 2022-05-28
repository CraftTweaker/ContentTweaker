package com.blamejared.contenttweaker.forge;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.plugin.ContentTweakerPlugin;
import com.blamejared.contenttweaker.core.api.plugin.ContentTweakerPluginProvider;
import com.blamejared.contenttweaker.core.api.plugin.CustomBracketRegistration;
import com.blamejared.contenttweaker.forge.zen.bracket.ContentTweakerForgeBrackets;

@ContentTweakerPlugin(ContentTweakerConstants.MOD_ID + ":forge")
public final class ContentTweakerForgePlugin implements ContentTweakerPluginProvider {
    @Override
    public void registerCustomBrackets(final CustomBracketRegistration registration) {
        ContentTweakerForgeBrackets.registerBrackets(registration);
    }
}
