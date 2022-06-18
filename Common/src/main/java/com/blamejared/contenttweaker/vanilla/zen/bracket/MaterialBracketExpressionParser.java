package com.blamejared.contenttweaker.vanilla.zen.bracket;

import com.blamejared.contenttweaker.core.api.zen.bracket.BracketHelper;
import com.blamejared.contenttweaker.vanilla.api.util.MaterialRegistry;
import com.blamejared.contenttweaker.vanilla.zen.rt.VanillaMetaFactory;
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

final class MaterialBracketExpressionParser implements BracketExpressionParser {
    private static final class MaterialMetaFactoryExpression extends ParsedExpression {
        private final ResourceLocation id;

        MaterialMetaFactoryExpression(final CodePosition position, final ResourceLocation id) {
            super(position);
            this.id = id;
        }

        @Override
        public IPartialExpression compile(final ExpressionScope scope) throws CompileException {
            final ParsedExpression runtimeClass = ParseUtil.staticMemberExpression(this.position, VanillaMetaFactory.ZEN_NAME);
            final ParsedExpression factoryMethod = new ParsedExpressionMember(this.position, runtimeClass, "material", null);
            final ParsedExpression id = BracketHelper.locationArgument(this.position, this.id);
            final ParsedCallArguments arguments = new ParsedCallArguments(Collections.emptyList(), Collections.singletonList(id));
            final ParsedExpression invocation = new ParsedExpressionCall(this.position, factoryMethod, arguments);
            return invocation.compile(scope);
        }

        @Override
        public boolean hasStrongType() {
            return true;
        }
    }
    MaterialBracketExpressionParser() {}

    static Stream<String> dump() {
        return MaterialRegistry.of()
                .knownMaterials()
                .map(Objects::toString)
                .map("<material:%s>"::formatted);
    }

    @Override
    public ParsedExpression parse(final CodePosition position, final ZSTokenParser tokens) throws ParseException {
        final String contents = ParseUtil.readBracketContent(position, tokens);
        final ResourceLocation location = BracketHelper.locationOrThrow(
                position,
                contents,
                () -> "Expected a material in the form <material:modid:name>, but got <material:%s>".formatted(contents)
        );
        return new MaterialMetaFactoryExpression(position, location);
    }
}
