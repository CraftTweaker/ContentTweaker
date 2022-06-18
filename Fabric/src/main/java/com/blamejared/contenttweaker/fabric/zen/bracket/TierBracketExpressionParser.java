package com.blamejared.contenttweaker.fabric.zen.bracket;

import com.blamejared.contenttweaker.core.api.zen.bracket.BracketHelper;
import com.blamejared.contenttweaker.fabric.api.util.ContentTweakerTierRegistry;
import com.blamejared.contenttweaker.fabric.zen.rt.TierMetaFactory;
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
import org.openzen.zenscript.parser.expression.ParsedExpressionInt;
import org.openzen.zenscript.parser.expression.ParsedExpressionMember;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

final class TierBracketExpressionParser implements BracketExpressionParser {
    private static final class InvokeTierMetaFactoryExpression extends ParsedExpression {
        private final ResourceLocation name;
        private final Integer level;

        InvokeTierMetaFactoryExpression(final CodePosition position, final ResourceLocation name, final Integer level) {
            super(position);
            this.name = name;
            this.level = level;
        }

        @Override
        public IPartialExpression compile(final ExpressionScope scope) throws CompileException {
            final ParsedExpression runtimeClass = ParseUtil.staticMemberExpression(this.position, TierMetaFactory.ZEN_NAME);
            final ParsedExpression factoryMethod = new ParsedExpressionMember(this.position, runtimeClass, "factory", null);
            final ParsedExpression argument = this.level != null? new ParsedExpressionInt(this.position, this.level.toString()) : BracketHelper.locationArgument(this.position, this.name);
            final ParsedCallArguments arguments = new ParsedCallArguments(Collections.emptyList(), List.of(argument));
            final ParsedExpression invocation = new ParsedExpressionCall(this.position, factoryMethod, arguments);
            return invocation.compile(scope);
        }

        @Override
        public boolean hasStrongType() {
            return true;
        }
    }

    static Stream<String> dump() {
        final Stream<String> first = ContentTweakerTierRegistry.of()
                .knownNames()
                .map(ResourceLocation::toString);
        final Stream<String> second = ContentTweakerTierRegistry.of()
                .knownLevels()
                .mapToObj(Integer::toString);
        return Stream.concat(first, second)
                .map("<tooltier:%s>"::formatted);
    }

    @Override
    public ParsedExpression parse(final CodePosition position, final ZSTokenParser tokens) throws ParseException {
        final String name = ParseUtil.readBracketContent(position, tokens);
        final Integer level = this.tryParse(name);

        if (level != null) { // We are querying by level
            if (level < 0) {
                throw new ParseException(position, "Invalid level for <tooltier:level> bracket: must be positive or zero but was " + level);
            }
            return new InvokeTierMetaFactoryExpression(position, null, level);
        }

        if (name.indexOf(':') == -1) {
            throw new ParseException(position, "Invalid name for <tooltier:modid:name>, but found " + name);
        }
        return new InvokeTierMetaFactoryExpression(position, BracketHelper.locationOrThrow(position, name), null);
    }

    private Integer tryParse(final String name) {
        try {
            return Integer.parseInt(name, 10);
        } catch (final NumberFormatException e) {
            return null;
        }
    }
}
