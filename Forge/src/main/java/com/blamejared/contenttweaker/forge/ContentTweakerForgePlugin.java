package com.blamejared.contenttweaker.forge;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.object.RegistryResolver;
import com.blamejared.contenttweaker.core.api.plugin.ContentTweakerPlugin;
import com.blamejared.contenttweaker.core.api.plugin.ContentTweakerPluginProvider;
import com.blamejared.contenttweaker.core.api.plugin.CustomBracketRegistration;
import com.blamejared.contenttweaker.core.api.plugin.RegistryResolverRegistration;
import com.blamejared.contenttweaker.forge.api.registry.TierRegistry;
import com.blamejared.contenttweaker.forge.zen.bracket.ContentTweakerForgeBrackets;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;

@ContentTweakerPlugin(ContentTweakerConstants.MOD_ID + ":forge")
public final class ContentTweakerForgePlugin implements ContentTweakerPluginProvider {
    @Override
    public void registerResolvers(final RegistryResolverRegistration registration) {
        registration.register(VanillaObjectTypes.TIER, RegistryResolver.of(VanillaObjectTypes.TIER, TierRegistry::of));
    }

    @Override
    public void registerCustomBrackets(final CustomBracketRegistration registration) {
        ContentTweakerForgeBrackets.registerBrackets(registration);
    }
}
