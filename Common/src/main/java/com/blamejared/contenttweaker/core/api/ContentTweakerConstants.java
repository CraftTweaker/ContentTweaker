package com.blamejared.contenttweaker.core.api;

import net.minecraft.resources.ResourceLocation;

public final class ContentTweakerConstants {

    public static final String MOD_ID = "contenttweaker";
    public static final String MOD_NAME = "ContentTweaker";

    public static final String CONTENT_LOADER_ID = MOD_ID;

    public static final ResourceLocation PRE_REGISTRY_LOAD_SOURCE = rl("pre_registry");

    private ContentTweakerConstants() {}

    public static ResourceLocation rl(final String id) {

        return new ResourceLocation(MOD_ID, id);
    }
}
