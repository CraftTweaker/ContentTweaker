package com.teamacronymcoders.contenttweaker.modules.vanilla.resources.sounds;

import com.teamacronymcoders.contenttweaker.api.ContentTweakerAPI;
import com.teamacronymcoders.contenttweaker.modules.vanilla.resources.ResourceBracketHandler;
import minetweaker.MineTweakerAPI;
import net.minecraft.block.SoundType;
import net.minecraft.util.SoundEvent;

public class SoundTypeBracketHandler extends ResourceBracketHandler {
    public SoundTypeBracketHandler() {
        super("SoundType", SoundTypeBracketHandler.class, 3);
    }

    public static ISoundTypeDefinition getSoundType(String name) {
        SoundType soundType = ContentTweakerAPI.getInstance().getSoundTypes().getResource(name);
        if(soundType == null) {
            MineTweakerAPI.logError("Could not find Sound Event for name: " + name);
        }
        return new SoundTypeDefinition(soundType);
    }
}
