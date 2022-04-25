package com.blamejared.contenttweaker.core.zen.bracket;

import com.blamejared.contenttweaker.core.ContentTweakerCore;
import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.object.ObjectFactory;
import com.blamejared.contenttweaker.core.api.object.ObjectFactoryMapping;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.registry.MetaRegistry;
import com.blamejared.contenttweaker.core.zen.ContentTweakerZenConstants;
import com.blamejared.contenttweaker.core.zen.rt.Unknown;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.api.util.ParseUtil;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.blamejared.crafttweaker.api.zencode.IZenClassRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.openzen.zencode.java.ZenCodeType;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zencode.shared.CompileExceptionCode;
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

public final class FactoryBracketExpressionParser implements BracketExpressionParser {

    @SuppressWarnings("unused")
    @ZenCodeType.Name(META_FACTORY_CLASS)
    @ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
    public static final class MetaFactory {
        private MetaFactory() {}

        @ZenCodeType.Method("factoryFor")
        public static <T, U extends ObjectFactory<T>> U factoryFor(final Class<T> reifiedT, final Class<U> reifiedU, final ResourceLocation registryId) {
            final MetaRegistry metaRegistry = ContentTweakerCore.core().metaRegistry();
            final ResourceKey<? extends Registry<T>> key = ResourceKey.createRegistryKey(registryId);
            final ObjectType<T> type = metaRegistry.objectTypes().get(key);
            final ObjectFactoryMapping<T, U> factoryClass = metaRegistry.factoryMappings().findMappingFor(type);
            return factoryClass.of();
        }

        @ZenCodeType.Method("factoryFor")
        public static ObjectFactory<Unknown> factoryFor() {
            return Unknown.Factory.INSTANCE;
        }
    }

    private static final class BracketMetaFactoryExpression<T> extends ParsedExpression {
        @FunctionalInterface
        private interface ParseOperation<T> {
            T run() throws ParseException;
        }

        private final ObjectType<T> type;

        public BracketMetaFactoryExpression(final CodePosition position, final ObjectType<T> type) {
            super(position);
            this.type = type;
        }

        @Override
        public IPartialExpression compile(final ExpressionScope scope) throws CompileException {
            final ParsedExpression runtimeClass = ParseUtil.staticMemberExpression(this.position, META_FACTORY_CLASS);
            final ParsedExpression factoryMethod = new ParsedExpressionMember(this.position, runtimeClass, "factoryFor", null);
            final ParsedCallArguments arguments = this.type == null? this.abstractCall() : this.concreteCall();
            final ParsedExpression invocation = new ParsedExpressionCall(this.position, factoryMethod, arguments);
            return invocation.compile(scope);
        }

        @Override
        public boolean hasStrongType() {
            return true;
        }

        private ParsedCallArguments abstractCall() {
            return ParsedCallArguments.NONE;
        }

        private ParsedCallArguments concreteCall() throws CompileException {
            return new ParsedCallArguments(List.of(this.findTypeGeneric(), this.findFactoryGeneric()), List.of(this.findArgument()));
        }

        private IParsedType findTypeGeneric() throws CompileException {
            return this.readParsedType(this.type.type());
        }

        private IParsedType findFactoryGeneric() throws CompileException {
            return this.readParsedType(ContentTweakerCore.core().metaRegistry().factoryMappings().findMappingFor(this.type).type());
        }

        private ParsedExpression findArgument() throws CompileException {
            return this.rethrowing(() -> ParseUtil.createResourceLocationArgument(this.position, this.type.id().location()));
        }

        private <L> IParsedType readParsedType(final Class<L> type) throws CompileException {
            return this.rethrowing(() -> ParseUtil.readParsedType(this.findClassNameFor(type), this.position));
        }

        private <L> String findClassNameFor(final Class<L> type) {
            final IScriptLoader loader = CraftTweakerAPI.getScriptRunManager().currentRunInfo().loader();
            final IZenClassRegistry classes = CraftTweakerAPI.getRegistry().getZenClassRegistry();
            return classes.getNameFor(loader, type).orElseThrow();
        }

        private <F> F rethrowing(final ParseOperation<F> operation) throws CompileException {
            try {
                return operation.run();
            } catch (final ParseException e) {
                final CompileException exception = new CompileException(this.position, CompileExceptionCode.PARSE_ERROR, e.message);
                exception.initCause(e);
                throw exception;
            }
        }
    }

    private static final String META_FACTORY_CLASS = ContentTweakerZenConstants.RT_PACKAGE + ".FactoryBracketMetaFactory";

    public static Stream<String> dump() {
        return null;
    }

    @Override
    public ParsedExpression parse(final CodePosition position, final ZSTokenParser tokens) throws ParseException {
        final ResourceLocation id = this.findRegistryId(position, tokens);
        final Registry<?> registry = this.grabRegistry(position, id);
        return this.createExpression(position, registry);
    }

    private ResourceLocation findRegistryId(final CodePosition position, final ZSTokenParser tokens) throws ParseException {
        final String bracketContents = ParseUtil.readBracketContent(position, tokens);
        return ContentTweakerBrackets.locationOrThrow(
                position,
                bracketContents,
                () -> "Expected a registry identifier in the form <factory:modid:name>, but instead found " + bracketContents
        );
    }

    private Registry<?> grabRegistry(final CodePosition position, final ResourceLocation targetedRegistry) throws ParseException {
        return Registry.REGISTRY.getOptional(targetedRegistry)
                .orElseThrow(() -> new ParseException(position, "No such registry with id " + targetedRegistry));
    }

    private <T> ParsedExpression createExpression(final CodePosition position, final Registry<T> registry) {
        return new BracketMetaFactoryExpression<>(position, this.grabType(registry));
    }

    @Nullable
    private <T> ObjectType<T> grabType(final Registry<? extends T> registry) {
        return GenericUtil.uncheck(ContentTweakerCore.core().metaRegistry().objectTypes().get(registry.key()));
    }
}
