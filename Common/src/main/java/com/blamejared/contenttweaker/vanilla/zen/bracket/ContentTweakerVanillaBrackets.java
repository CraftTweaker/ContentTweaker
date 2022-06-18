package com.blamejared.contenttweaker.vanilla.zen.bracket;

import com.blamejared.contenttweaker.core.api.plugin.CustomBracketRegistration;

public final class ContentTweakerVanillaBrackets {
    private ContentTweakerVanillaBrackets() {}

    public static void register(final CustomBracketRegistration registration) {
        registration.registerBracket("item", new ItemBracketExpressionParser()); // Dump already available through CT
        registration.registerBracket("tab", new CreativeTabBracketExpressionParser(), CreativeTabBracketExpressionParser::dump);
        registration.registerBracket("material", new MaterialBracketExpressionParser(), MaterialBracketExpressionParser::dump);
        registration.registerBracket("materialcolor", new MaterialColorBracketExpressionParser(), MaterialColorBracketExpressionParser::dump);
    }
}
