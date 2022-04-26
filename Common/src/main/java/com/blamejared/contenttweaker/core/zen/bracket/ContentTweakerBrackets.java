package com.blamejared.contenttweaker.core.zen.bracket;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.crafttweaker.api.plugin.IBracketParserRegistrationHandler;
import net.minecraft.ResourceLocationException;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.lexer.ParseException;
import org.openzen.zenscript.parser.BracketExpressionParser;

import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

public final class ContentTweakerBrackets {
    private ContentTweakerBrackets() {}

    public static void registerBrackets(final IBracketParserRegistrationHandler handler) {
        bracket(handler, "factory", new FactoryBracketExpressionParser(), FactoryBracketExpressionParser::dump);
    }

    private static void bracket(final IBracketParserRegistrationHandler handler, final String name, final BracketExpressionParser parser, final Supplier<Stream<String>> dumper) {
        handler.registerParserFor(
                ContentTweakerConstants.CONTENT_LOADER_ID,
                name,
                parser,
                new IBracketParserRegistrationHandler.DumperData(null, dumper)
        );
    }

    static ResourceLocation locationOrThrow(final CodePosition position, final String in) throws ParseException {
        return locationOrThrow(position, in, () -> "Unable to convert given string \"" + in + "\" to resource location");
    }

    static ResourceLocation locationOrThrow(final CodePosition position, final String in, final Supplier<String> message) throws ParseException {
        try {
            return new ResourceLocation(Objects.requireNonNull(in, "in"));
        } catch (final NullPointerException | ResourceLocationException e) {
            throw new ParseException(position, message.get(), e);
        }
    }
}
