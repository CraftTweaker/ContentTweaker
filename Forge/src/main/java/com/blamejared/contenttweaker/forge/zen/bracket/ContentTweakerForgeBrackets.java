package com.blamejared.contenttweaker.forge.zen.bracket;

import com.blamejared.contenttweaker.core.api.plugin.CustomBracketRegistration;

public final class ContentTweakerForgeBrackets {
    private ContentTweakerForgeBrackets() {}

    public static void registerBrackets(final CustomBracketRegistration registration) {
        registration.registerBracket("tooltier", new TierBracketExpressionParser(), TierBracketExpressionParser::dump);
    }
}
