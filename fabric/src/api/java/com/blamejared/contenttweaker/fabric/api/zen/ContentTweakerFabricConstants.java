package com.blamejared.contenttweaker.fabric.api.zen;

import com.blamejared.contenttweaker.core.api.zen.ContentTweakerZenConstants;

public final class ContentTweakerFabricConstants {
    public static final String FABRIC_PACKAGE_MARKER = ".fabric";

    public static final String FABRIC_BUILDER_PACKAGE = ContentTweakerZenConstants.MAIN_PACKAGE + ".builder" + FABRIC_PACKAGE_MARKER;
    public static final String FABRIC_RT_PACKAGE = ContentTweakerZenConstants.RT_PACKAGE + FABRIC_PACKAGE_MARKER;

    public static final String TAB_BUILDER_PACKAGE = FABRIC_BUILDER_PACKAGE + ".tab";

    private ContentTweakerFabricConstants() {}
}
