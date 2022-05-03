package com.blamejared.contenttweaker.core.api.zen.bracket;

import com.blamejared.contenttweaker.core.zen.rt.ResourceLocationNative;
import com.blamejared.crafttweaker.api.util.ParseUtil;
import net.minecraft.ResourceLocationException;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zencode.shared.CompileExceptionCode;
import org.openzen.zenscript.lexer.ParseException;
import org.openzen.zenscript.parser.expression.ParsedCallArguments;
import org.openzen.zenscript.parser.expression.ParsedExpression;
import org.openzen.zenscript.parser.expression.ParsedExpressionCall;
import org.openzen.zenscript.parser.expression.ParsedExpressionMember;
import org.openzen.zenscript.parser.expression.ParsedExpressionString;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public final class BracketHelper {
    @FunctionalInterface
    public interface ParseOperation<T> {
        T execute() throws ParseException;
    }

    private BracketHelper() {}

    public static ResourceLocation locationOrThrow(final CodePosition position, final String in) throws ParseException {
        return locationOrThrow(position, in, () -> "Unable to convert given string \"" + in + "\" to resource location");
    }

    public static ResourceLocation locationOrThrow(final CodePosition position, final String in, final Supplier<String> message) throws ParseException {
        Objects.requireNonNull(position);
        Objects.requireNonNull(message);
        try {
            return new ResourceLocation(Objects.requireNonNull(in, "null"));
        } catch (final NullPointerException | ResourceLocationException e) {
            throw new ParseException(position, message.get(), e);
        }
    }

    public static ParsedExpression locationArgument(final CodePosition position, final ResourceLocation location) {
        Objects.requireNonNull(position);
        Objects.requireNonNull(location);
        final ParsedExpression receiver = ParseUtil.staticMemberExpression(position, ResourceLocationNative.CLASS_NAME);
        final ParsedExpression of = new ParsedExpressionMember(position, receiver, "of", null);
        final ParsedExpressionString namespace = new ParsedExpressionString(position, location.getNamespace(), false);
        final ParsedExpressionString path = new ParsedExpressionString(position, location.getPath(), false);
        final ParsedCallArguments arguments = new ParsedCallArguments(null, List.of(namespace, path));
        return new ParsedExpressionCall(position, of, arguments);
    }

    public static <F> F parseToCompile(final CodePosition position, final ParseOperation<F> operation) throws CompileException {
        try {
            return operation.execute();
        } catch (final ParseException e) {
            final CompileException exception = new CompileException(position, CompileExceptionCode.PARSE_ERROR, e.message);
            exception.initCause(e);
            throw exception;
        }
    }
}
