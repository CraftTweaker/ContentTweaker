package com.teamacronymcoders.contenttweaker.api.wrappers.world;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.contenttweaker.World")
public interface IWorld {
    @ZenMethod
    boolean isRemote();

    Object getInternal();
}
