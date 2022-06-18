package com.blamejared.contenttweaker.core.zen.bracket;

import com.blamejared.contenttweaker.core.api.zen.bracket.BracketHelper;
import com.blamejared.crafttweaker.api.util.ParseUtil;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zenscript.codemodel.partial.IPartialExpression;
import org.openzen.zenscript.codemodel.scope.ExpressionScope;
import org.openzen.zenscript.lexer.ParseException;
import org.openzen.zenscript.lexer.ZSTokenParser;
import org.openzen.zenscript.parser.BracketExpressionParser;
import org.openzen.zenscript.parser.expression.ParsedExpression;

import java.util.Objects;
import java.util.regex.Pattern;

final class ResourceLocationBracketExpressionParser implements BracketExpressionParser {
    private static final class ResourceLocationExpression extends ParsedExpression {
        private final String location;

        ResourceLocationExpression(final CodePosition position, final String namespace, final String path) {
            super(position);
            this.location = "%s:%s".formatted(Objects.requireNonNull(namespace), Objects.requireNonNull(path));
        }

        @Override
        public IPartialExpression compile(final ExpressionScope scope) throws CompileException {
            final ResourceLocation location = BracketHelper.parseToCompile(this.position, () -> BracketHelper.locationOrThrow(this.position, this.location));
            return BracketHelper.locationArgument(this.position, location).compile(scope);
        }

        @Override
        public boolean hasStrongType() {
            return true;
        }
    }

    @Override
    public ParsedExpression parse(final CodePosition position, final ZSTokenParser tokens) throws ParseException {
        final String contents = ParseUtil.readBracketContent(position, tokens);
        if (contents.indexOf(':') == -1) {
            throw new IllegalStateException("Expected resource bracket in the format <resource:modid:path>, but found <resource:%s>".formatted(contents));
        }
        final String[] split = contents.split(Pattern.quote(":"), 2);
        return new ResourceLocationExpression(position, split[0], split[1]);
    }
}
