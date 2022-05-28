package com.blamejared.contenttweaker.fabric.zen.bracket;

import com.blamejared.contenttweaker.core.api.plugin.CustomBracketRegistration;

public final class ContentTweakerFabricBrackets {
    private ContentTweakerFabricBrackets() {}

    public static void registerBrackets(final CustomBracketRegistration registration) {
        registration.registerBracket("tooltier", new TierBracketExpressionParser(), TierBracketExpressionParser::dump);
    }
}
