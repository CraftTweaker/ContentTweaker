package com.blamejared.contenttweaker.vanilla.util;

import com.blamejared.contenttweaker.core.api.zen.object.SimpleReference;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.SoundType;

import java.util.Objects;

// TODO("Remove in 1.19 as this is now not needed anymore because Forge grew to its senses")
public final class ReferencingSoundType extends SoundType {
    private final SimpleReference<SoundEvent> breakSound;
    private final SimpleReference<SoundEvent> stepSound;
    private final SimpleReference<SoundEvent> placeSound;
    private final SimpleReference<SoundEvent> hitSound;
    private final SimpleReference<SoundEvent> fallSound;

    private ReferencingSoundType(
            final float volume,
            final float pitch,
            final SimpleReference<SoundEvent> breakSound,
            final SimpleReference<SoundEvent> stepSound,
            final SimpleReference<SoundEvent> placeSound,
            final SimpleReference<SoundEvent> hitSound,
            final SimpleReference<SoundEvent> fallSound
    ) {
        super(volume, pitch, null, null, null, null, null);
        this.breakSound = breakSound;
        this.stepSound = stepSound;
        this.placeSound = placeSound;
        this.hitSound = hitSound;
        this.fallSound = fallSound;
    }

    public static ReferencingSoundType of(
            final float volume,
            final float pitch,
            final SimpleReference<SoundEvent> breakSound,
            final SimpleReference<SoundEvent> stepSound,
            final SimpleReference<SoundEvent> placeSound,
            final SimpleReference<SoundEvent> hitSound,
            final SimpleReference<SoundEvent> fallSound
    ) {
        return new ReferencingSoundType(
                volume,
                pitch,
                Objects.requireNonNull(breakSound),
                Objects.requireNonNull(stepSound),
                Objects.requireNonNull(placeSound),
                Objects.requireNonNull(hitSound),
                Objects.requireNonNull(fallSound)
        );
    }

    @Override
    public SoundEvent getBreakSound() {
        return this.breakSound.get();
    }

    @Override
    public SoundEvent getStepSound() {
        return this.stepSound.get();
    }

    @Override
    public SoundEvent getPlaceSound() {
        return this.placeSound.get();
    }

    @Override
    public SoundEvent getHitSound() {
        return this.hitSound.get();
    }

    @Override
    public SoundEvent getFallSound() {
        return this.fallSound.get();
    }
}
