package com.teamacronymcoders.contenttweaker.modules.vanilla.resources.sounds;

import com.teamacronymcoders.contenttweaker.api.ContentTweakerAPI;
import com.teamacronymcoders.contenttweaker.modules.vanilla.resources.ResourceBracketHandler;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.BracketHandler;
import net.minecraft.util.SoundEvent;

@BracketHandler
public class SoundEventBracketHandler extends ResourceBracketHandler {
    public SoundEventBracketHandler() {
        super("SoundEvent", SoundEventBracketHandler.class, 2);
    }

    public static ISoundEventDefinition getSoundEvent(String name) {
        SoundEvent soundEvent = ContentTweakerAPI.getInstance().getSoundEvents().getResource(name);
        if (soundEvent == null) {
            CraftTweakerAPI.logError("Could not find Sound Event for name: " + name);
        }
        return new SoundEventDefinition(soundEvent);
    }
}
