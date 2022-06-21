package com.blamejared.contenttweaker.vanilla.zen.bracket;

import com.blamejared.contenttweaker.core.api.zen.bracket.BracketHelper;
import com.blamejared.contenttweaker.core.api.zen.bracket.ReferenceExpression;
import com.blamejared.contenttweaker.core.api.zen.object.SimpleReference;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;
import com.blamejared.crafttweaker.api.util.ParseUtil;
import com.google.gson.reflect.TypeToken;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import org.openzen.zencode.shared.CodePosition;
import org.openzen.zenscript.lexer.ParseException;
import org.openzen.zenscript.lexer.ZSTokenParser;
import org.openzen.zenscript.parser.BracketExpressionParser;
import org.openzen.zenscript.parser.expression.ParsedExpression;

public final class SoundEventBracketExpressionParser implements BracketExpressionParser {
    SoundEventBracketExpressionParser() {}

    @Override
    public ParsedExpression parse(final CodePosition position, final ZSTokenParser tokens) throws ParseException {
        final String contents = ParseUtil.readBracketContent(position, tokens);
        final ResourceLocation location = BracketHelper.locationOrThrow(
                position,
                contents,
                () -> "Expected a sound event in the form <soundevent:modid:name>, but got <soundevent:%s>".formatted(contents)
        );
        return new ReferenceExpression<>(position, VanillaObjectTypes.SOUND_EVENT, new TypeToken<SimpleReference<SoundEvent>>() {}, location);
    }
}
