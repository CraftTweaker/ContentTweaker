package com.blamejared.contenttweaker.vanilla.zen.bracket;

import com.blamejared.contenttweaker.core.api.zen.bracket.BracketHelper;
import com.blamejared.contenttweaker.core.api.zen.bracket.ReferenceExpression;
import com.blamejared.contenttweaker.core.api.zen.object.SimpleReference;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;
import com.blamejared.contenttweaker.vanilla.api.zen.object.TierReference;
import com.blamejared.crafttweaker.api.util.ParseUtil;
import com.google.gson.reflect.TypeToken;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.lexer.ParseException;
import org.openzen.zenscript.lexer.ZSTokenParser;
import org.openzen.zenscript.parser.BracketExpressionParser;
import org.openzen.zenscript.parser.expression.ParsedExpression;

public final class TierBracketExpressionParser implements BracketExpressionParser {
    TierBracketExpressionParser() {}

    @Override
    public ParsedExpression parse(final CodePosition position, final ZSTokenParser tokens) throws ParseException {
        final String contents = ParseUtil.readBracketContent(position, tokens);
        final ResourceLocation location = BracketHelper.locationOrThrow(
                position,
                contents,
                () -> "Expected a tier in the form <tier:modid:name>, but got <tier:%s>".formatted(contents)
        );
        return new ReferenceExpression<>(position, VanillaObjectTypes.TIER, new TypeToken<TierReference>() {}, location);
    }
}
