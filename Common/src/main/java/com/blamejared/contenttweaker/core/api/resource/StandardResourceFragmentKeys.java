package com.blamejared.contenttweaker.core.api.resource;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import net.minecraft.server.packs.PackType;

public final class StandardResourceFragmentKeys {
    public static final ResourceFragment.Key CONTENT_TWEAKER_ASSETS = new ResourceFragment.Key(PackType.CLIENT_RESOURCES, ContentTweakerConstants.MOD_ID);
    public static final ResourceFragment.Key CONTENT_TWEAKER_DATA = new ResourceFragment.Key(PackType.SERVER_DATA, ContentTweakerConstants.MOD_ID);

    public static final ResourceFragment.Key MINECRAFT_ASSETS = new ResourceFragment.Key(PackType.CLIENT_RESOURCES, "minecraft");
    public static final ResourceFragment.Key MINECRAFT_DATA = new ResourceFragment.Key(PackType.SERVER_DATA, "minecraft");

    // TODO("Maybe move the below ones somewhere else?")
    public static final ResourceFragment.Key FORGE_ASSETS = new ResourceFragment.Key(PackType.CLIENT_RESOURCES, "forge");
    public static final ResourceFragment.Key FORGE_DATA = new ResourceFragment.Key(PackType.SERVER_DATA, "forge");

    public static final ResourceFragment.Key FABRIC_BASE_ASSETS = new ResourceFragment.Key(PackType.CLIENT_RESOURCES, "fabric");
    public static final ResourceFragment.Key FABRIC_BASE_DATA = new ResourceFragment.Key(PackType.SERVER_DATA, "fabric");

    public static final ResourceFragment.Key FABRIC_COMMON_ASSETS = new ResourceFragment.Key(PackType.CLIENT_RESOURCES, "c");
    public static final ResourceFragment.Key FABRIC_COMMON_DATA = new ResourceFragment.Key(PackType.SERVER_DATA, "c");

    private StandardResourceFragmentKeys() {}
}
