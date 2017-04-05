package com.teamacronymcoders.contenttweaker.modules.vanilla.resources.sounds;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.contenttweaker.SoundEvent")
public interface ISoundEventDefinition {
    @ZenMethod
    String getSoundName();

    Object getInternal();
}
