package com.blamejared.contenttweaker.vanilla.zen.bracket;

import com.blamejared.contenttweaker.core.api.plugin.CustomBracketRegistration;
import com.blamejared.crafttweaker.api.plugin.IBracketParserRegistrationHandler;
import org.openzen.zenscript.parser.BracketExpressionParser;

import java.util.function.Supplier;
import java.util.stream.Stream;

public final class ContentTweakerVanillaBrackets {
    private ContentTweakerVanillaBrackets() {}

    public static void register(final CustomBracketRegistration registration) {
        withCommand(registration, "item", new ItemBracketExpressionParser(), "contenttweaker_items", ItemBracketExpressionParser::dump);
        registration.registerBracket("tab", new CreativeTabBracketExpressionParser(), CreativeTabBracketExpressionParser::dump);
    }

    private static void withCommand(
            final CustomBracketRegistration reg,
            final String name,
            final BracketExpressionParser parser,
            final String sub,
            final Supplier<Stream<String>> dumper
    ) {
        reg.registerBracket(name, parser, new IBracketParserRegistrationHandler.DumperData(sub, dumper));
    }
}
