package com.blamejared.contenttweaker.forge.api.zen;

import com.blamejared.contenttweaker.core.api.zen.ContentTweakerZenConstants;

public final class ContentTweakerForgeConstants {
    public static final String FORGE_PACKAGE_MARKER = ".forge";

    public static final String FORGE_BUILDER_PACKAGE = ContentTweakerZenConstants.MAIN_PACKAGE + ".builder" + FORGE_PACKAGE_MARKER;
    public static final String FORGE_RT_PACKAGE = ContentTweakerZenConstants.RT_PACKAGE + FORGE_PACKAGE_MARKER;

    public static final String TAB_BUILDER_PACKAGE = FORGE_BUILDER_PACKAGE + ".tab";

    private ContentTweakerForgeConstants() {}
}
