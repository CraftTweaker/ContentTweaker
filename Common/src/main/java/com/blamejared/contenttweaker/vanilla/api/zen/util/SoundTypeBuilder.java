package com.blamejared.contenttweaker.vanilla.api.zen.util;

import com.blamejared.contenttweaker.core.api.ContentTweakerApi;
import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.vanilla.api.action.sound.CreateSoundTypeAction;
import com.blamejared.contenttweaker.vanilla.api.util.SoundTypeRegistry;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.object.SoundEventReference;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.util.NameUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.SoundType;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;

@ZenCodeType.Name(ContentTweakerVanillaConstants.VANILLA_UTIL_PACKAGE + ".SoundTypeBuilder")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class SoundTypeBuilder {
    // TODO("Remove in 1.19 as this is now not needed anymore because Forge grew to its senses")
    private static final class ReferencingSoundType extends SoundType {
        private final SoundEventReference breakSound;
        private final SoundEventReference stepSound;
        private final SoundEventReference placeSound;
        private final SoundEventReference hitSound;
        private final SoundEventReference fallSound;

        ReferencingSoundType(final SoundTypeBuilder builder) {
            super(Objects.requireNonNull(builder).volume(), builder.pitch(), null, null, null, null, null);
            this.breakSound = builder.breakSound();
            this.stepSound = builder.stepSound();
            this.placeSound = builder.placeSound();
            this.hitSound = builder.hitSound();
            this.fallSound = builder.fallSound();
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

    private Float volume;
    private Float pitch;
    private SoundEventReference breakSound;
    private SoundEventReference stepSound;
    private SoundEventReference placeSound;
    private SoundEventReference hitSound;
    private SoundEventReference fallSound;

    private SoundTypeBuilder() {
        this.volume = null;
        this.pitch = null;
        this.breakSound = null;
        this.stepSound = null;
        this.placeSound = null;
        this.hitSound = null;
        this.fallSound = null;
    }

    static SoundTypeBuilder of() {
        return new SoundTypeBuilder();
    }

    @ZenCodeType.Method("volume")
    public SoundTypeBuilder volume(final float volume) {
        this.volume = volume;
        return this;
    }

    @ZenCodeType.Method("pitch")
    public SoundTypeBuilder pitch(final float pitch) {
        this.pitch = pitch;
        return this;
    }

    @ZenCodeType.Method("breakSound")
    public SoundTypeBuilder breakSound(final SoundEventReference sound) {
        this.breakSound = Objects.requireNonNull(sound);
        return this;
    }

    @ZenCodeType.Method("stepSound")
    public SoundTypeBuilder stepSound(final SoundEventReference sound) {
        this.stepSound = Objects.requireNonNull(sound);
        return this;
    }

    @ZenCodeType.Method("placeSound")
    public SoundTypeBuilder placeSound(final SoundEventReference sound) {
        this.placeSound = Objects.requireNonNull(sound);
        return this;
    }

    @ZenCodeType.Method("hitSound")
    public SoundTypeBuilder hitSound(final SoundEventReference sound) {
        this.hitSound = Objects.requireNonNull(sound);
        return this;
    }

    @ZenCodeType.Method("fallSound")
    public SoundTypeBuilder fallSound(final SoundEventReference sound) {
        this.fallSound = Objects.requireNonNull(sound);
        return this;
    }

    @ZenCodeType.Method("build")
    public SoundTypeReference build(final String name) {
        if (this.volume == null) {
            throw new IllegalStateException("Missing volume specification for sound type builder");
        }
        if (this.pitch == null) {
            throw new IllegalStateException("Missing pitch specification for sound type builder");
        }
        if (this.breakSound == null) {
            throw new IllegalStateException("Missing break sound specification for sound type builder");
        }
        if (this.stepSound == null) {
            throw new IllegalStateException("Missing step sound specification for sound type builder");
        }
        if (this.placeSound == null) {
            throw new IllegalStateException("Missing place sound specification for sound type builder");
        }
        if (this.hitSound == null) {
            throw new IllegalStateException("Missing hit sound specification for sound type builder");
        }
        if (this.fallSound == null) {
            throw new IllegalStateException("Missing fall sound specification for sound type builder");
        }
        final ResourceLocation id = ContentTweakerConstants.rl(
                NameUtil.fixing(
                        name,
                        (fix, mistakes) -> CraftTweakerAPI.LOGGER.warn(
                                "The given name '{}' is not valid for sound types and it has thus been fixed to '{}'.\nMistakes:\n{}",
                                name,
                                fix,
                                String.join("\n", mistakes)
                        )
                )
        );
        return SoundTypeReference.of(id, () -> {
            final SoundType[] soundType = new SoundType[1];
            ContentTweakerApi.apply(CreateSoundTypeAction.of(id, () -> soundType[0] = SoundTypeRegistry.of().register(id, () -> new ReferencingSoundType(this))));
            return soundType[0];
        });
    }

    float volume() {
        return this.volume;
    }

    float pitch() {
        return this.pitch;
    }

    SoundEventReference breakSound() {
        return this.breakSound;
    }

    SoundEventReference stepSound() {
        return this.stepSound;
    }

    SoundEventReference placeSound() {
        return this.placeSound;
    }

    SoundEventReference hitSound() {
        return this.hitSound;
    }

    SoundEventReference fallSound() {
        return this.fallSound;
    }
}
