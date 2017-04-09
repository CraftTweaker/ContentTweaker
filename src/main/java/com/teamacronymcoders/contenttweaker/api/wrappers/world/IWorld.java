package com.teamacronymcoders.contenttweaker.api.wrappers.world;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenClass("mods.contenttweaker.World")
public interface IWorld {
    @ZenGetter("remote")
    boolean isRemote();
}
