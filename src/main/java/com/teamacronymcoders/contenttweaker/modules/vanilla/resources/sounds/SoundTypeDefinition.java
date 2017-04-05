package com.teamacronymcoders.contenttweaker.modules.vanilla.resources.sounds;

import net.minecraft.block.SoundType;

public class SoundTypeDefinition implements ISoundTypeDefinition {
    private SoundType soundType;

    public SoundTypeDefinition(SoundType soundType) {
        this.soundType = soundType;
    }

    @Override
    public float getVolume() {
        return soundType.getVolume();
    }

    @Override
    public float getPitch() {
        return soundType.getPitch();
    }

    @Override
    public ISoundEventDefinition getBreakSound() {
        return new SoundEventDefinition(soundType.getBreakSound());
    }

    @Override
    public ISoundEventDefinition getStepSound() {
        return new SoundEventDefinition(soundType.getStepSound());
    }

    @Override
    public ISoundEventDefinition getPlaceSound() {
        return new SoundEventDefinition(soundType.getPlaceSound());
    }

    @Override
    public ISoundEventDefinition getHitSound() {
        return new SoundEventDefinition(soundType.getHitSound());
    }

    @Override
    public ISoundEventDefinition getFallSound() {
        return new SoundEventDefinition(soundType.getFallSound());
    }

    @Override
    public Object getInternal() {
        return this.soundType;
    }
}
