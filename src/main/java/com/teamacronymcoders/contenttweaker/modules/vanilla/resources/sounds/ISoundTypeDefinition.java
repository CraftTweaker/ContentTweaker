package com.teamacronymcoders.contenttweaker.modules.vanilla.resources.sounds;

import com.teamacronymcoders.contenttweaker.api.ICTObject;
import net.minecraft.block.SoundType;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.contenttweaker.SoundType")
public interface ISoundTypeDefinition extends ICTObject<SoundType> {
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
