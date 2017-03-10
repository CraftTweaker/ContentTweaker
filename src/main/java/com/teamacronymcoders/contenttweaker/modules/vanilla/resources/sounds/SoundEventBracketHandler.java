package com.teamacronymcoders.contenttweaker.modules.vanilla.resources.sounds;

import com.teamacronymcoders.contenttweaker.api.ContentTweakerAPI;
import com.teamacronymcoders.contenttweaker.modules.vanilla.resources.ResourceBracketHandler;
import com.teamacronymcoders.contenttweaker.modules.vanilla.resources.materials.IMaterialDefinition;
import com.teamacronymcoders.contenttweaker.modules.vanilla.resources.materials.MaterialDefinition;
import minetweaker.MineTweakerAPI;
import net.minecraft.block.material.Material;
import net.minecraft.util.SoundEvent;

public class SoundEventBracketHandler extends ResourceBracketHandler {
    public SoundEventBracketHandler() {
        super("SoundEvent", SoundEventBracketHandler.class, 3);
    }

    public static ISoundEventDefinition getSoundEvent(String name) {
        SoundEvent soundEvent = ContentTweakerAPI.getInstance().getSoundEvents().getResource(name);
        if(soundEvent == null) {
            MineTweakerAPI.logError("Could not find Sound Event for name: " + name);
        }
        return new SoundEventDefinition(soundEvent);
    }
}
