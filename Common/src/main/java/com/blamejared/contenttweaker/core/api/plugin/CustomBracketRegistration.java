package com.blamejared.contenttweaker.core.api.plugin;

import com.blamejared.crafttweaker.api.plugin.IBracketParserRegistrationHandler;
import org.openzen.zenscript.parser.BracketExpressionParser;

import java.util.function.Supplier;
import java.util.stream.Stream;

public interface CustomBracketRegistration {
    void registerBracket(final String name, final BracketExpressionParser parser, final IBracketParserRegistrationHandler.DumperData dumperData);

    default void registerBracket(final String name, final BracketExpressionParser parser, final Supplier<Stream<String>> dumper) {
        this.registerBracket(name, parser, new IBracketParserRegistrationHandler.DumperData(null, dumper));
    }

    default void registerBracket(final String name, final BracketExpressionParser parser) {
        this.registerBracket(name, parser, (IBracketParserRegistrationHandler.DumperData) null);
    }
}
