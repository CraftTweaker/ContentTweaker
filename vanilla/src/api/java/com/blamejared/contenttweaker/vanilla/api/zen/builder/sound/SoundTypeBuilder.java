package com.blamejared.contenttweaker.vanilla.api.zen.builder.sound;

import com.blamejared.contenttweaker.core.api.ContentTweakerApi;
import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.action.RegisterObjectAction;
import com.blamejared.contenttweaker.core.api.object.ObjectHolder;
import com.blamejared.contenttweaker.core.api.zen.object.SimpleReference;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;
import com.blamejared.contenttweaker.vanilla.api.util.ReferencingSoundType;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.SoundType;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;

@ZenCodeType.Name(ContentTweakerVanillaConstants.SOUND_BUILDER_PACKAGE + ".SoundTypeBuilder")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class SoundTypeBuilder {
    private Float volume;
    private Float pitch;
    private SimpleReference<SoundEvent> breakSound;
    private SimpleReference<SoundEvent> stepSound;
    private SimpleReference<SoundEvent> placeSound;
    private SimpleReference<SoundEvent> hitSound;
    private SimpleReference<SoundEvent> fallSound;

    private SoundTypeBuilder() {
        this.volume = null;
        this.pitch = null;
        this.breakSound = null;
        this.stepSound = null;
        this.placeSound = null;
        this.hitSound = null;
        this.fallSound = null;
    }

    public static SoundTypeBuilder of() {
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
    public SoundTypeBuilder breakSound(final SimpleReference<SoundEvent> sound) {
        this.breakSound = Objects.requireNonNull(sound);
        return this;
    }

    @ZenCodeType.Method("stepSound")
    public SoundTypeBuilder stepSound(final SimpleReference<SoundEvent> sound) {
        this.stepSound = Objects.requireNonNull(sound);
        return this;
    }

    @ZenCodeType.Method("placeSound")
    public SoundTypeBuilder placeSound(final SimpleReference<SoundEvent> sound) {
        this.placeSound = Objects.requireNonNull(sound);
        return this;
    }

    @ZenCodeType.Method("hitSound")
    public SoundTypeBuilder hitSound(final SimpleReference<SoundEvent> sound) {
        this.hitSound = Objects.requireNonNull(sound);
        return this;
    }

    @ZenCodeType.Method("fallSound")
    public SoundTypeBuilder fallSound(final SimpleReference<SoundEvent> sound) {
        this.fallSound = Objects.requireNonNull(sound);
        return this;
    }

    @ZenCodeType.Method("build")
    public SimpleReference<SoundType> build(final String name) {
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
        final ResourceLocation id = ContentTweakerConstants.rl(name);
        final ObjectHolder<SoundType> holder = ObjectHolder.of(VanillaObjectTypes.SOUND_TYPE, id, this::create);
        ContentTweakerApi.apply(RegisterObjectAction.of(holder));
        return SimpleReference.of(VanillaObjectTypes.SOUND_TYPE, id);
    }

    private SoundType create() {
        return ReferencingSoundType.of(this.volume, this.pitch, this.breakSound, this.stepSound, this.placeSound, this.hitSound, this.fallSound);
    }
}
