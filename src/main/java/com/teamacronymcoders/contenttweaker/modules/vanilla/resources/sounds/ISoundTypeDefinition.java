package com.teamacronymcoders.contenttweaker.modules.vanilla.resources.sounds;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.contenttweaker.SoundType")
public interface ISoundTypeDefinition {
    @ZenMethod
    float getVolume();

    @ZenMethod
    float getPitch();

    @ZenMethod
    ISoundEventDefinition getBreakSound();

    @ZenMethod
    ISoundEventDefinition getStepSound();

    @ZenMethod
    ISoundEventDefinition getPlaceSound();

    @ZenMethod
    ISoundEventDefinition getHitSound();

    @ZenMethod
    ISoundEventDefinition getFallSound();
}
