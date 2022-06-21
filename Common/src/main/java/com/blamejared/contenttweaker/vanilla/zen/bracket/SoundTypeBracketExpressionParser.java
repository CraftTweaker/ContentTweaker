package com.blamejared.contenttweaker.vanilla.zen.bracket;

import com.blamejared.contenttweaker.core.api.zen.bracket.BracketHelper;
import com.blamejared.contenttweaker.core.api.zen.bracket.ReferenceExpression;
import com.blamejared.contenttweaker.core.api.zen.object.SimpleReference;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;
import com.blamejared.crafttweaker.api.util.ParseUtil;
import com.google.gson.reflect.TypeToken;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.SoundType;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.lexer.ParseException;
import org.openzen.zenscript.lexer.ZSTokenParser;
import org.openzen.zenscript.parser.BracketExpressionParser;
import org.openzen.zenscript.parser.expression.ParsedExpression;

final class SoundTypeBracketExpressionParser implements BracketExpressionParser {
    SoundTypeBracketExpressionParser() {}

    @Override
    public ParsedExpression parse(final CodePosition position, final ZSTokenParser tokens) throws ParseException {
        final String contents = ParseUtil.readBracketContent(position, tokens);
        final ResourceLocation location = BracketHelper.locationOrThrow(
                position,
                contents,
                () -> "Expected a sound type in the form <soundtype:modid:name>, but got <soundtype:%s>".formatted(contents)
        );
        return new ReferenceExpression<>(position, VanillaObjectTypes.SOUND_TYPE, new TypeToken<SimpleReference<SoundType>>() {}, location);
    }
}

