package com.teamacronymcoders.contenttweaker.modules.vanilla.resources.sounds;

import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class SoundEventDefinition implements ISoundEventDefinition {
    private SoundEvent soundEvent;
    private String soundName;

    public SoundEventDefinition(SoundEvent soundEvent) {
        this.soundEvent = soundEvent;
    }

    @Override
    public String getSoundName() {
        if (this.soundName == null) {
            this.soundName = ReflectionHelper.getPrivateValue(SoundEvent.class, this.soundEvent, "soundName", "field_187506_b").toString();
        }
        return this.soundName;
    }

    @Override
    public SoundEvent getInternal() {
        return soundEvent;
    }
}
