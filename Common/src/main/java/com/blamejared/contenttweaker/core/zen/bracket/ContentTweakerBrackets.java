package com.blamejared.contenttweaker.core.zen.bracket;

import com.blamejared.contenttweaker.core.ContentTweakerCore;
import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.crafttweaker.api.plugin.IBracketParserRegistrationHandler;
import org.openzen.zenscript.parser.BracketExpressionParser;

import java.util.function.Supplier;
import java.util.stream.Stream;

public final class ContentTweakerBrackets {
    private ContentTweakerBrackets() {}

    public static void registerBrackets(final IBracketParserRegistrationHandler handler) {
        bracket(handler, "factory", new FactoryBracketExpressionParser(), FactoryBracketExpressionParser::dump);
        bracket(handler, "reference", new ReferenceBracketExpressionParser(), ReferenceBracketExpressionParser::dump);
        handler.registerParserFor(ContentTweakerConstants.CONTENT_LOADER_ID, "resource", new ResourceLocationBracketExpressionParser(), null);
        ContentTweakerCore.core().registerPluginBrackets(handler);
    }

    private static void bracket(final IBracketParserRegistrationHandler handler, final String name, final BracketExpressionParser parser, final Supplier<Stream<String>> dumper) {
        handler.registerParserFor(
                ContentTweakerConstants.CONTENT_LOADER_ID,
                name,
                parser,
                new IBracketParserRegistrationHandler.DumperData(null, dumper)
        );
    }
}
