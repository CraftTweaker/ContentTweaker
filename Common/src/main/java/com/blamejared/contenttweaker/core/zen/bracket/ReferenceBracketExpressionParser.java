package com.blamejared.contenttweaker.core.zen.bracket;

import com.blamejared.contenttweaker.core.ContentTweakerCore;
import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.zen.bracket.BracketHelper;
import com.blamejared.contenttweaker.core.api.zen.object.Reference;
import com.blamejared.contenttweaker.core.registry.MetaRegistry;
import com.blamejared.contenttweaker.core.zen.ContentTweakerZenConstants;
import com.blamejared.contenttweaker.core.zen.rt.Unknown;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
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

import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public final class ReferenceBracketExpressionParser implements BracketExpressionParser {
    @SuppressWarnings("unused")
    @ZenCodeType.Name(META_FACTORY_CLASS)
    @ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
    public static final class MetaFactory {
        private MetaFactory() {}

        @ZenCodeType.Method("of")
        public static <T> Reference<T> of(final Class<T> reifiedT, final ResourceLocation registryId, final ResourceLocation id) {
            final MetaRegistry metaRegistry = ContentTweakerCore.core().metaRegistry();
            final ResourceKey<? extends Registry<T>> key = ResourceKey.createRegistryKey(registryId);
            final ObjectType<T> type = metaRegistry.objectTypes().get(key);
            return new Reference<>(type, id) {}; // TODO("")
        }
    }

    private static final class ReferenceExpression<T> extends ParsedExpression {
        private final ObjectType<T> type;
        private final ResourceLocation registry;
        private final ResourceLocation id;

        ReferenceExpression(final CodePosition position, final ObjectType<T> type, final ResourceLocation registry, final ResourceLocation id) {
            super(position);
            this.type = type;
            this.registry = registry;
            this.id = id;
        }

        @Override
        public IPartialExpression compile(final ExpressionScope scope) throws CompileException {
            final ParsedExpression metaFactory = ParseUtil.staticMemberExpression(this.position, META_FACTORY_CLASS);
            final ParsedExpression of = new ParsedExpressionMember(this.position, metaFactory, "of", null);
            final ParsedCallArguments arguments = new ParsedCallArguments(this.parsedType(), this.arguments());
            final ParsedExpression invocation = new ParsedExpressionCall(this.position, of, arguments);
            return invocation.compile(scope);
        }

        private List<IParsedType> parsedType() throws CompileException {
            final IScriptLoader loader = CraftTweakerAPI.getScriptRunManager().currentRunInfo().loader();
            final IZenClassRegistry classes = CraftTweakerAPI.getRegistry().getZenClassRegistry();
            final Class<?> type = this.type == null? Unknown.class : this.type.type();
            final String name = classes.getNameFor(loader, type).orElseThrow();
            final IParsedType parsedType = BracketHelper.parseToCompile(this.position, () -> ParseUtil.readParsedType(name, this.position));
            return List.of(parsedType);
        }

        private List<ParsedExpression> arguments() {
            return List.of(
                    BracketHelper.locationArgument(this.position, this.registry),
                    BracketHelper.locationArgument(this.position, this.id)
            );
        }

        @Override
        public boolean hasStrongType() {
            return true;
        }
    }

    private record EntryData(ResourceLocation registry, ResourceLocation object) {
        @Override
        public String toString() {
            return "<reference:%s:%s>".formatted(this.registry(), this.object());
        }
    }

    private static final String META_FACTORY_CLASS = ContentTweakerZenConstants.RT_PACKAGE + ".ReferenceBracketMetaFactory";

    public static Stream<String> dump() {
        return Registry.REGISTRY.stream()
                .sorted(Comparator.comparing(it -> it.key().location()))
                .flatMap(ReferenceBracketExpressionParser::makeEntryData)
                .map(EntryData::toString);
    }

    private static <T> Stream<EntryData> makeEntryData(final Registry<T> registry) {
        final ResourceLocation id = registry.key().location();
        return registry.keySet()
                .stream()
                .map(it -> new EntryData(id, it));
    }

    @Override
    public ParsedExpression parse(final CodePosition position, final ZSTokenParser tokens) throws ParseException {
        // <reference:minecraft:item:minecraft:leather>
        final String contents = ParseUtil.readBracketContent(position, tokens);
        final int colons = contents.replaceAll("[^:]", "").length();
        final String[] split = contents.split(Pattern.quote(":"), 4);
        return switch (colons) {
            case 1 -> this.parseVanillaVanilla(position, split);
            case 2 -> this.parseVanillaModded(position, split);
            case 3 -> this.parseModdedModded(position, split);
            default -> throw this.fail(position, contents);
        };
    }

    private ParsedExpression parseVanillaVanilla(final CodePosition position, final String[] content) throws ParseException {
        assert content.length == 2;
        final ResourceLocation registry = BracketHelper.locationOrThrow(position, content[0]);
        final ResourceLocation id = BracketHelper.locationOrThrow(position, content[1]);
        return this.createExpression(position, registry, id);
    }

    private ParsedExpression parseVanillaModded(final CodePosition position, final String[] content) throws ParseException {
        assert content.length == 3;
        final ResourceLocation registry = BracketHelper.locationOrThrow(position, content[0]);
        final ResourceLocation id = BracketHelper.locationOrThrow(position, content[1] + ':' + content[2]);
        return this.createExpression(position, registry, id);
    }

    private ParsedExpression parseModdedModded(final CodePosition position, final String[] content) throws ParseException {
        assert content.length == 4;
        final ResourceLocation registry = BracketHelper.locationOrThrow(position, content[0] + ':' + content[1]);
        final ResourceLocation id = BracketHelper.locationOrThrow(position, content[2] + ':' + content[3]);
        return this.createExpression(position, registry, id);
    }

    private ParsedExpression createExpression(final CodePosition position, final ResourceLocation registry, final ResourceLocation id) {
        // Scripts run before registry creation and object registration, so no validation has to be performed
        return new ReferenceExpression<>(position, this.grabType(ResourceKey.createRegistryKey(registry)), registry, id);
    }

    private ParseException fail(final CodePosition position, final String contents) throws ParseException {
        final String message = "Invalid contents \"" + contents +
                "\" for reference bracket: expected bracket in format <reference:registryNamespace:registryId:objectNamespace:objectId>" +
                " or one of the following short-hand forms: <reference:registryId:objectId>, <reference:registryId:objectNamespace:objectId>";
        throw new ParseException(position, message);
    }

    @Nullable
    private <T> ObjectType<T> grabType(final ResourceKey<? extends Registry<T>> registryKey) {
        return ContentTweakerCore.core().metaRegistry().objectTypes().get(registryKey);
    }
}
