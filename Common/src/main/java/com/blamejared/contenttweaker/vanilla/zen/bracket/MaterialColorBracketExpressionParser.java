package com.blamejared.contenttweaker.vanilla.zen.bracket;

import com.blamejared.contenttweaker.core.api.zen.bracket.BracketHelper;
import com.blamejared.contenttweaker.core.api.zen.bracket.ReferenceExpression;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;
import com.blamejared.contenttweaker.vanilla.api.zen.object.MaterialColorReference;
import com.blamejared.crafttweaker.api.util.ParseUtil;
import com.google.gson.reflect.TypeToken;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.lexer.ParseException;
import org.openzen.zenscript.lexer.ZSTokenParser;
import org.openzen.zenscript.parser.BracketExpressionParser;
import org.openzen.zenscript.parser.expression.ParsedExpression;

public final class MaterialColorBracketExpressionParser implements BracketExpressionParser {
    MaterialColorBracketExpressionParser() {}

    @Override
    public ParsedExpression parse(final CodePosition position, final ZSTokenParser tokens) throws ParseException {
        final String content = ParseUtil.readBracketContent(position, tokens);
        final ResourceLocation location = BracketHelper.locationOrThrow(
                position,
                content,
                () -> "Expected a material color in the form <materialcolor:modid:name>, but got <materialcolor:%s>".formatted(content)
        );
        return new ReferenceExpression<>(position, VanillaObjectTypes.MATERIAL_COLOR, new TypeToken<MaterialColorReference>() {}, location);
    }
}
