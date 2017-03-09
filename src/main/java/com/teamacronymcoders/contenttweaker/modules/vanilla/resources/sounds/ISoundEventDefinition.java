package com.teamacronymcoders.contenttweaker.modules.vanilla.resources.sounds;

import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("mods.contenttweaker.SoundEvent")
public interface ISoundEventDefinition {
    String getSoundName();

    Object getInternal();
}
