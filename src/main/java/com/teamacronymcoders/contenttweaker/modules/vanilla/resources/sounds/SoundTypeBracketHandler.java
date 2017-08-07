package com.teamacronymcoders.contenttweaker.modules.vanilla.resources.sounds;

import com.teamacronymcoders.contenttweaker.api.ContentTweakerAPI;
import com.teamacronymcoders.contenttweaker.modules.vanilla.resources.ResourceBracketHandler;
import crafttweaker.CraftTweakerAPI;
import net.minecraft.block.SoundType;

public class SoundTypeBracketHandler extends ResourceBracketHandler {
    public SoundTypeBracketHandler() {
        super("SoundType", SoundTypeBracketHandler.class, 2);
    }

    public static ISoundTypeDefinition getSoundType(String name) {
        SoundType soundType = ContentTweakerAPI.getInstance().getSoundTypes().getResource(name);
        if(soundType == null) {
            CraftTweakerAPI.logError("Could not find Sound Type for name: " + name);
        }
        return new SoundTypeDefinition(soundType);
    }
}
