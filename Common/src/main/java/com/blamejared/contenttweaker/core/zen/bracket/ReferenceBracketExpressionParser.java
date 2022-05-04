package com.blamejared.contenttweaker.core.zen.bracket;

import com.blamejared.contenttweaker.core.ContentTweakerCore;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.zen.bracket.BracketHelper;
import com.blamejared.contenttweaker.core.api.zen.bracket.ReferenceExpression;
import com.blamejared.crafttweaker.api.util.ParseUtil;
import com.google.gson.reflect.TypeToken;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
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
        return createExpression(position, this.grabType(ResourceKey.createRegistryKey(registry)), id);
    }

    private <T> ParsedExpression createExpression(final CodePosition position, final ObjectType<T> type, final ResourceLocation id) {
        // Scripts run before registry creation and object registration, so no validation has to be performed
        return new ReferenceExpression<>(position, type, new TypeToken<>() {}, id);
    }

    private ParseException fail(final CodePosition position, final String contents) throws ParseException {
        final String message = "Invalid contents \"" + contents +
                "\" for reference bracket: expected bracket in format <reference:registryNamespace:registryId:objectNamespace:objectId>" +
                " or one of the following short-hand forms: <reference:registryId:objectId>, <reference:registryId:objectNamespace:objectId>";
        throw new ParseException(position, message);
    }

    private <T> ObjectType<T> grabType(final ResourceKey<? extends Registry<T>> registryKey) {
        return ContentTweakerCore.core().metaRegistry().objectTypes().getOrUnknown(registryKey);
    }
}
