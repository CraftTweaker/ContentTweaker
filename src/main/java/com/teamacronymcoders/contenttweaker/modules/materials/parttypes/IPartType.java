package com.teamacronymcoders.contenttweaker.modules.materials.parttypes;

import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("mods.contenttweaker.PartType")
public interface IPartType {
    String getName();

    Object getInternal();
}
