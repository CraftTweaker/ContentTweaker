package com.blamejared.contenttweaker.core.zen.bracket;

import com.blamejared.contenttweaker.core.ContentTweakerCore;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.object.ReferenceFactory;
import com.blamejared.contenttweaker.core.api.registry.GameRegistry;
import com.blamejared.contenttweaker.core.api.zen.bracket.BracketHelper;
import com.blamejared.contenttweaker.core.api.zen.bracket.ReferenceExpression;
import com.blamejared.contenttweaker.core.api.zen.object.Reference;
import com.blamejared.crafttweaker.api.util.ParseUtil;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.lexer.ParseException;
import org.openzen.zenscript.lexer.ZSTokenParser;
import org.openzen.zenscript.parser.BracketExpressionParser;
import org.openzen.zenscript.parser.expression.ParsedExpression;

import java.util.Comparator;
import java.util.regex.Pattern;
import java.util.stream.Stream;

final class ReferenceBracketExpressionParser implements BracketExpressionParser {
    private record EntryData(ResourceLocation registry, ResourceLocation object) {
        @Override
        public String toString() {
            return "<reference:%s:%s>".formatted(this.registry(), this.object());
        }
    }

    public static Stream<String> dump() {
        return ContentTweakerCore.core()
                .metaRegistry()
                .objectTypes()
                .allTypes()
                .stream()
                .flatMap(ReferenceBracketExpressionParser::makeEntryData)
                .sorted()
                .map(EntryData::toString);
    }

    private static <T> Stream<EntryData> makeEntryData(final ObjectType<T> type) {
        final ResourceLocation id = type.id();
        final GameRegistry<T> registry = registryOf(type);
        return registry.stream()
                .map(registry::nameOf)
                .map(it -> new EntryData(id, it));
    }

    private static <T> GameRegistry<T> registryOf(final ObjectType<T> type) {
        return ContentTweakerCore.core()
                .metaRegistry()
                .registryResolvers()
                .findResolverFor(type)
                .registry();
    }

    @Override
    public ParsedExpression parse(final CodePosition position, final ZSTokenParser tokens) throws ParseException {
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

    private ParsedExpression createExpression(final CodePosition position, final ResourceLocation typeId, final ResourceLocation id) throws ParseException {
        return createExpression(position, typeId, this.grabType(typeId), id);
    }

    private <T, U extends Reference<T>> ParsedExpression createExpression(
            final CodePosition position,
            final ResourceLocation typeId,
            final ObjectType<T> type,
            final ResourceLocation id
    ) throws ParseException {
        // Scripts run before registry creation and object registration, so no validation has to be performed
        if (type == null) {
            throw new ParseException(position, "The given type " + typeId + " is not known: unable to construct a reference to it");
        }

        final ReferenceFactory<T, U> factory = ContentTweakerCore.core().metaRegistry().referenceFactories().findFactoryFor(type);
        return new ReferenceExpression<>(position, type, factory.type(), id);
    }

    private ParseException fail(final CodePosition position, final String contents) throws ParseException {
        final String message = "Invalid contents \"" + contents +
                "\" for reference bracket: expected bracket in format <reference:typeNamespace:typeId:objectNamespace:objectId>" +
                " or one of the following short-hand forms: <reference:typeId:objectId>, <reference:typeId:objectNamespace:objectId>";
        throw new ParseException(position, message);
    }

    private <T> ObjectType<T> grabType(final ResourceLocation typeId) {
        return ContentTweakerCore.core().metaRegistry().objectTypes().get(typeId);
    }
}
