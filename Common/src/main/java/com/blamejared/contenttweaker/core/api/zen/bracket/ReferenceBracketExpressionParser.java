package com.blamejared.contenttweaker.core.api.zen.bracket;

import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.zen.object.Reference;
import com.blamejared.crafttweaker.api.util.ParseUtil;
import com.google.gson.reflect.TypeToken;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.lexer.ParseException;
import org.openzen.zenscript.lexer.ZSTokenParser;
import org.openzen.zenscript.parser.BracketExpressionParser;
import org.openzen.zenscript.parser.expression.ParsedExpression;

import java.util.Objects;

public final class ReferenceBracketExpressionParser<T, U extends Reference<T>> implements BracketExpressionParser {
    private final String formatMessage;
    private final ObjectType<T> type;
    private final TypeToken<U> token;

    private ReferenceBracketExpressionParser(final String formatMessage, final ObjectType<T> type, final TypeToken<U> token) {
        this.formatMessage = formatMessage;
        this.type = type;
        this.token = token;
    }

    public static <T, U extends Reference<T>> ReferenceBracketExpressionParser<T, U> of(final String formatMessage, final ObjectType<T> type, final TypeToken<U> token) {
        return new ReferenceBracketExpressionParser<>(Objects.requireNonNull(formatMessage), Objects.requireNonNull(type), Objects.requireNonNull(token));
    }

    @Override
    public ParsedExpression parse(final CodePosition position, final ZSTokenParser tokens) throws ParseException {
        final String contents = ParseUtil.readBracketContent(position, tokens);
        if (contents.indexOf(':') == -1) {
            throw new ParseException(position, this.formatMessage.formatted(contents));
        }
        final ResourceLocation location = BracketHelper.locationOrThrow(
                position,
                contents,
                () -> this.formatMessage.formatted(contents)
        );
        return new ReferenceExpression<>(position, this.type, this.token, location);
    }
}
