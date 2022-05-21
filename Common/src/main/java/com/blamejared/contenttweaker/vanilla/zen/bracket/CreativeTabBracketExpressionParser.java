package com.blamejared.contenttweaker.vanilla.zen.bracket;

import com.blamejared.contenttweaker.vanilla.mixin.CreativeModeTabAccessor;
import com.blamejared.contenttweaker.vanilla.zen.rt.TabMetaFactory;
import com.blamejared.crafttweaker.api.util.ParseUtil;
import com.google.common.base.CaseFormat;
import net.minecraft.world.item.CreativeModeTab;
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
import org.openzen.zenscript.parser.expression.ParsedExpressionString;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

final class CreativeTabBracketExpressionParser implements BracketExpressionParser {
    private static final class InvokeTabMetaFactoryExpression extends ParsedExpression {
        private final String name;

        InvokeTabMetaFactoryExpression(final CodePosition position, final String name) {
            super(position);
            this.name = name;
        }

        @Override
        public IPartialExpression compile(final ExpressionScope scope) throws CompileException {
            final ParsedExpression runtimeClass = ParseUtil.staticMemberExpression(this.position, TabMetaFactory.ZEN_NAME);
            final ParsedExpression factoryMethod = new ParsedExpressionMember(this.position, runtimeClass, "factory", null);
            final ParsedExpression name = new ParsedExpressionString(this.position, this.name, false);
            final ParsedCallArguments arguments = new ParsedCallArguments(Collections.emptyList(), Collections.singletonList(name));
            final ParsedExpression invocation = new ParsedExpressionCall(this.position, factoryMethod, arguments);
            return invocation.compile(scope);
        }

        @Override
        public boolean hasStrongType() {
            return true;
        }
    }

    static Stream<String> dump() {
        return Arrays.stream(CreativeModeTab.TABS)
                .map(CreativeModeTabAccessor.class::cast)
                .map(CreativeModeTabAccessor::contenttweaker$langId)
                .map(CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.LOWER_UNDERSCORE)::convert)
                .map("<tab:%s>"::formatted);
    }

    @Override
    public ParsedExpression parse(final CodePosition position, final ZSTokenParser tokens) throws ParseException {
        final String tabName = ParseUtil.readBracketContent(position, tokens);
        return new InvokeTabMetaFactoryExpression(position, tabName);
    }
}
