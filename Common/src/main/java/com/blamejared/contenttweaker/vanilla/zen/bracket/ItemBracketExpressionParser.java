package com.blamejared.contenttweaker.vanilla.zen.bracket;

import com.blamejared.contenttweaker.core.api.zen.bracket.BracketHelper;
import com.blamejared.contenttweaker.core.api.zen.bracket.ReferenceExpression;
import com.blamejared.contenttweaker.vanilla.object.VanillaObjectTypes;
import com.blamejared.contenttweaker.vanilla.api.zen.object.ItemReference;
import com.blamejared.crafttweaker.api.util.ParseUtil;
import com.google.gson.reflect.TypeToken;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.lexer.ParseException;
import org.openzen.zenscript.lexer.ZSTokenParser;
import org.openzen.zenscript.parser.BracketExpressionParser;
import org.openzen.zenscript.parser.expression.ParsedExpression;

import java.util.stream.Stream;

public final class ItemBracketExpressionParser implements BracketExpressionParser {
    @Override
    public ParsedExpression parse(final CodePosition position, final ZSTokenParser tokens) throws ParseException {
        final String contents = ParseUtil.readBracketContent(position, tokens);
        if (contents.indexOf(':') == -1) {
            throw new ParseException(position, "Expected <item:modid:name>, but found illegal bracket <item:" + contents + ">");
        }
        final ResourceLocation id = BracketHelper.locationOrThrow(
                position,
                contents,
                () -> "Unable to convert \"" + contents + "\" to a valid location: expected <item:modid:name>"
        );
        return new ReferenceExpression<>(position, VanillaObjectTypes.ITEM, new TypeToken<ItemReference>() {}, id);
    }
}
