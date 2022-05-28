package com.blamejared.contenttweaker.forge.zen.bracket;

import com.blamejared.contenttweaker.core.api.zen.bracket.BracketHelper;
import com.blamejared.contenttweaker.forge.zen.rt.TierMetaFactory;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.api.util.HandleUtil;
import com.blamejared.crafttweaker.api.util.ParseUtil;
import com.google.common.collect.BiMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraftforge.common.TierSortingRegistry;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zenscript.codemodel.partial.IPartialExpression;
import org.openzen.zenscript.codemodel.scope.ExpressionScope;
import org.openzen.zenscript.lexer.ParseException;
import org.openzen.zenscript.lexer.ZSTokenParser;
import org.openzen.zenscript.parser.BracketExpressionParser;
import org.openzen.zenscript.parser.expression.ParsedCallArguments;
import org.openzen.zenscript.parser.expression.ParsedExpression;
import org.openzen.zenscript.parser.expression.ParsedExpressionBool;
import org.openzen.zenscript.parser.expression.ParsedExpressionCall;
import org.openzen.zenscript.parser.expression.ParsedExpressionMember;

import java.lang.invoke.VarHandle;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

final class TierBracketExpressionParser implements BracketExpressionParser {
    private static final class InvokeTierMetaFactoryExpression extends ParsedExpression {
        private final ResourceLocation name;

        InvokeTierMetaFactoryExpression(final CodePosition position, final ResourceLocation name) {
            super(position);
            this.name = name;
        }

        @Override
        public IPartialExpression compile(final ExpressionScope scope) throws CompileException {
            final ParsedExpression runtimeClass = ParseUtil.staticMemberExpression(this.position, TierMetaFactory.ZEN_NAME);
            final ParsedExpression factoryMethod = new ParsedExpressionMember(this.position, runtimeClass, "factory", null);
            final ParsedExpression name = BracketHelper.locationArgument(this.position, this.name);
            final ParsedExpression marker = new ParsedExpressionBool(this.position, true);
            final ParsedCallArguments arguments = new ParsedCallArguments(Collections.emptyList(), List.of(name, marker));
            final ParsedExpression invocation = new ParsedExpressionCall(this.position, factoryMethod, arguments);
            return invocation.compile(scope);
        }

        @Override
        public boolean hasStrongType() {
            return true;
        }
    }

    private static final VarHandle TIERS = HandleUtil.linkField(TierSortingRegistry.class, "tiers", "Lcom/google/common/collect/BiMap;");

    static Stream<String> dump() {
        return GenericUtil.<BiMap<ResourceLocation, Tier>>uncheck(TIERS.get())
                .keySet()
                .stream()
                .map(ResourceLocation::toString)
                .map("<tooltier:%s>"::formatted);
    }

    @Override
    public ParsedExpression parse(final CodePosition position, final ZSTokenParser tokens) throws ParseException {
        final String name = ParseUtil.readBracketContent(position, tokens);
        if (name.indexOf(':') == -1) {
            throw new ParseException(position, "Expected <tooltier:modid:name>, but found illegal bracket <tooltier:" + name + ">");
        }
        return new InvokeTierMetaFactoryExpression(position, BracketHelper.locationOrThrow(position, name));
    }
}
