package com.blamejared.contenttweaker.vanilla.zen.bracket;

import com.blamejared.contenttweaker.core.api.zen.bracket.ReferenceExpression;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;
import com.blamejared.contenttweaker.vanilla.api.registry.CreativeTabRegistry;
import com.blamejared.contenttweaker.vanilla.api.zen.object.CreativeTabReference;
import com.blamejared.crafttweaker.api.util.ParseUtil;
import com.google.gson.reflect.TypeToken;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.lexer.ParseException;
import org.openzen.zenscript.lexer.ZSTokenParser;
import org.openzen.zenscript.parser.BracketExpressionParser;
import org.openzen.zenscript.parser.expression.ParsedExpression;

final class CreativeTabBracketExpressionParser implements BracketExpressionParser {
    CreativeTabBracketExpressionParser() {}

    @Override
    public ParsedExpression parse(final CodePosition position, final ZSTokenParser tokens) throws ParseException {
        final String tabName = ParseUtil.readBracketContent(position, tokens);
        final ResourceLocation fakeId = CreativeTabRegistry.fromId(tabName);
        return new ReferenceExpression<>(position, VanillaObjectTypes.CREATIVE_TAB, new TypeToken<CreativeTabReference>() {}, fakeId);
    }
}
