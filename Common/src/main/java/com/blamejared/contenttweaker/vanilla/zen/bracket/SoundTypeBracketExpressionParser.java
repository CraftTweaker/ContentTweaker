package com.blamejared.contenttweaker.vanilla.zen.bracket;

import com.blamejared.contenttweaker.core.api.zen.bracket.BracketHelper;
import com.blamejared.contenttweaker.vanilla.api.util.SoundTypeRegistry;
import com.blamejared.contenttweaker.vanilla.zen.rt.SoundTypeMetaFactory;
import com.blamejared.crafttweaker.api.util.ParseUtil;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zenscript.codemodel.partial.IPartialExpression;
import org.openzen.zenscript.codemodel.scope.ExpressionScope;
import org.openzen.zenscript.lexer.ParseException;
import org.openzen.zenscript.lexer.ZSTokenParser;
import org.openzen.zenscript.parser.BracketExpressionParser;
import org.openzen.zenscript.parser.expression.ParsedCallArguments;
import org.openzen.zenscript.parser.expression.ParsedExpression;
import org.openzen.zenscript.parser.expression.ParsedExpressionCall;
import org.openzen.zenscript.parser.expression.ParsedExpressionMember;

import java.util.Collections;
import java.util.Objects;
import java.util.stream.Stream;

final class SoundTypeBracketExpressionParser implements BracketExpressionParser {
    private static final class SoundTypeMetaFactoryExpression extends ParsedExpression {
        private final ResourceLocation id;

        SoundTypeMetaFactoryExpression(final CodePosition position, final ResourceLocation id) {
            super(position);
            this.id = id;
        }

        @Override
        public IPartialExpression compile(final ExpressionScope scope) throws CompileException {
            final ParsedExpression runtimeClass = ParseUtil.staticMemberExpression(this.position, SoundTypeMetaFactory.ZEN_NAME);
            final ParsedExpression factoryMethod = new ParsedExpressionMember(this.position, runtimeClass, "factory", null);
            final ParsedExpression id = BracketHelper.locationArgument(position, this.id);
            final ParsedCallArguments arguments = new ParsedCallArguments(Collections.emptyList(), Collections.singletonList(id));
            final ParsedExpression invocation = new ParsedExpressionCall(this.position, factoryMethod, arguments);
            return invocation.compile(scope);
        }

        @Override
        public boolean hasStrongType() {
            return true;
        }
    }
    SoundTypeBracketExpressionParser() {}

    static Stream<String> dump() {
        return SoundTypeRegistry.of()
                .knownTypes()
                .map(Objects::toString)
                .map("<soundtype:%s>"::formatted);
    }

    @Override
    public ParsedExpression parse(final CodePosition position, final ZSTokenParser tokens) throws ParseException {
        final String contents = ParseUtil.readBracketContent(position, tokens);
        final ResourceLocation location = BracketHelper.locationOrThrow(
                position,
                contents,
                () -> "Expected a sound type in the form <soundtype:modid:name>, but got <material:%s>".formatted(contents)
        );
        return new SoundTypeMetaFactoryExpression(position, location);
    }
}

