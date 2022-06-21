package com.blamejared.contenttweaker.core.zen.bracket;

import com.blamejared.contenttweaker.core.ContentTweakerCore;
import com.blamejared.contenttweaker.core.api.object.ObjectFactory;
import com.blamejared.contenttweaker.core.api.object.ObjectFactoryMapping;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.zen.ContentTweakerZenConstants;
import com.blamejared.contenttweaker.core.api.zen.bracket.BracketHelper;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.util.ParseUtil;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.blamejared.crafttweaker.api.zencode.IZenClassRegistry;
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
    private static final class BracketMetaFactoryExpression<T, U extends ObjectFactory<T>> extends ParsedExpression {
        private final ObjectType<T> type;
        private final ObjectFactoryMapping<T, U> factoryMapping;

        BracketMetaFactoryExpression(final CodePosition position, final ObjectType<T> type, final ObjectFactoryMapping<T, U> factoryMapping) {
            super(position);
            this.type = type;
            this.factoryMapping = factoryMapping;
        }

        @Override
        public IPartialExpression compile(final ExpressionScope scope) throws CompileException {
            final ParsedExpression runtimeClass = ParseUtil.staticMemberExpression(this.position, ContentTweakerZenConstants.CORE_META_FACTORY_ZEN_NAME);
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
            return this.readParsedType(this.factoryMapping.type());
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
        return ContentTweakerCore.core()
                .metaRegistry()
                .objectTypes()
                .allTypes()
                .stream()
                .map(ObjectType::id)
                .sorted()
                .map(ResourceLocation::toString)
                .map("<factory:%s>"::formatted);
    }

    @Override
    public ParsedExpression parse(final CodePosition position, final ZSTokenParser tokens) throws ParseException {
        final ResourceLocation id = this.findRegistryId(position, tokens);
        return this.createExpression(position, id);
    }

    private ResourceLocation findRegistryId(final CodePosition position, final ZSTokenParser tokens) throws ParseException {
        final String bracketContents = ParseUtil.readBracketContent(position, tokens);
        return BracketHelper.locationOrThrow(
                position,
                bracketContents,
                () -> "Expected an object type identifier in the form <factory:modid:name>, but instead found " + bracketContents
        );
    }

    private <T> ParsedExpression createExpression(final CodePosition position, final ResourceLocation typeId) throws ParseException {
        final ObjectType<T> type = this.grabType(position, typeId);
        return new BracketMetaFactoryExpression<>(position, type, this.grabFactoryMapping(type));
    }

    private <T> ObjectType<T> grabType(final CodePosition position, final ResourceLocation typeId) throws ParseException {
        final ObjectType<T> type = ContentTweakerCore.core().metaRegistry().objectTypes().get(typeId);
        if (type == null) {
            throw new ParseException(position, "Type for '" + typeId + "' is not known: unable to create a factory for it");
        }
        return type;
    }

    private <T, U extends ObjectFactory<T>> ObjectFactoryMapping<T, U> grabFactoryMapping(final ObjectType<T> type) {
        return ContentTweakerCore.core().metaRegistry().factoryMappings().findMappingFor(type);
    }
}
