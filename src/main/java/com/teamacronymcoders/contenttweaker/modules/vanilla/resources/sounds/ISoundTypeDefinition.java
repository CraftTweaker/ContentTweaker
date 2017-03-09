package com.teamacronymcoders.contenttweaker.modules.vanilla.resources.sounds;

import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("mods.contenttweaker.SoundType")
public interface ISoundTypeDefinition {
    float getVolume();

    float getPitch();


}
