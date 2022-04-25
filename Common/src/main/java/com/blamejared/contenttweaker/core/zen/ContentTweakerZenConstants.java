package com.blamejared.contenttweaker.core.zen;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;

public final class ContentTweakerZenConstants {
    public static final String MAIN_PACKAGE = ContentTweakerConstants.MOD_ID;
    public static final String RT_PACKAGE = MAIN_PACKAGE + "._rt";
    public static final String UNKNOWN_CLASS_MARKER = RT_PACKAGE + ".Unknown";
    public static final String UNKNOWN_FACTORY_CLASS_MARKER = RT_PACKAGE + ".UnknownFactory";

    private ContentTweakerZenConstants() {}
}
