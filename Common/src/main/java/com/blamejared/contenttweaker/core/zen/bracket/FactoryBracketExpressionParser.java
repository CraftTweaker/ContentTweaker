package com.blamejared.contenttweaker.core.zen.bracket;

import com.blamejared.contenttweaker.core.ContentTweakerCore;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.zen.bracket.BracketHelper;
import com.blamejared.contenttweaker.core.zen.rt.CoreMetaFactory;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.util.ParseUtil;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.blamejared.crafttweaker.api.zencode.IZenClassRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
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
import org.openzen.zenscript.parser.type.IParsedType;

import java.util.List;
import java.util.stream.Stream;

final class FactoryBracketExpressionParser implements BracketExpressionParser {
    private static final class BracketMetaFactoryExpression<T> extends ParsedExpression {
        private final ObjectType<T> type;

        public BracketMetaFactoryExpression(final CodePosition position, final ObjectType<T> type) {
            super(position);
            this.type = type;
        }

        @Override
        public IPartialExpression compile(final ExpressionScope scope) throws CompileException {
            final ParsedExpression runtimeClass = ParseUtil.staticMemberExpression(this.position, CoreMetaFactory.ZEN_NAME);
            final ParsedExpression factoryMethod = new ParsedExpressionMember(this.position, runtimeClass, "factory", null);
            final ParsedCallArguments arguments = this.makeCall();
            final ParsedExpression invocation = new ParsedExpressionCall(this.position, factoryMethod, arguments);
            return invocation.compile(scope);
        }

        @Override
        public boolean hasStrongType() {
            return true;
        }

        private ParsedCallArguments makeCall() throws CompileException {
            return new ParsedCallArguments(List.of(this.findTypeGeneric(), this.findFactoryGeneric()), List.of(this.findArgument()));
        }

        private IParsedType findTypeGeneric() throws CompileException {
            return this.readParsedType(this.type.type());
        }

        private IParsedType findFactoryGeneric() throws CompileException {
            return this.readParsedType(ContentTweakerCore.core().metaRegistry().factoryMappings().findMappingFor(this.type).type());
        }

        private ParsedExpression findArgument() throws CompileException {
            return BracketHelper.parseToCompile(this.position, () -> BracketHelper.locationArgument(this.position, this.type.id()));
        }

        private <L> IParsedType readParsedType(final Class<L> type) throws CompileException {
            return BracketHelper.parseToCompile(this.position, () -> ParseUtil.readParsedType(this.findClassNameFor(type), this.position));
        }

        private <L> String findClassNameFor(final Class<L> type) {
            final IScriptLoader loader = CraftTweakerAPI.getScriptRunManager().currentRunInfo().loader();
            final IZenClassRegistry classes = CraftTweakerAPI.getRegistry().getZenClassRegistry();
            return classes.getNameFor(loader, type).orElseThrow();
        }
    }

    public static Stream<String> dump() {
        return Registry.REGISTRY.stream()
                .map(Registry::key)
                .map(ResourceKey::location)
                .sorted()
                .map(ResourceLocation::toString)
                .map("<factory:%s>"::formatted);
    }

    @Override
    public ParsedExpression parse(final CodePosition position, final ZSTokenParser tokens) throws ParseException {
        final ResourceLocation id = this.findRegistryId(position, tokens);
        return this.createExpression(position, ResourceKey.createRegistryKey(id));
    }

    private ResourceLocation findRegistryId(final CodePosition position, final ZSTokenParser tokens) throws ParseException {
        final String bracketContents = ParseUtil.readBracketContent(position, tokens);
        return BracketHelper.locationOrThrow(
                position,
                bracketContents,
                () -> "Expected a registry identifier in the form <factory:modid:name>, but instead found " + bracketContents
        );
    }

    private <T> ParsedExpression createExpression(final CodePosition position, final ResourceKey<? extends Registry<T>> registryKey) throws ParseException {
        return new BracketMetaFactoryExpression<>(position, this.grabType(position, registryKey));
    }

    private <T> ObjectType<T> grabType(final CodePosition position, final ResourceKey<? extends Registry<T>> registryKey) throws ParseException {
        final ObjectType<T> type = ContentTweakerCore.core().metaRegistry().objectTypes().get(registryKey);
        if (type == null) {
            throw new ParseException(position, "Type for " + registryKey + " is not known: unable to create a factory for it");
        }
        return type;
    }
}
