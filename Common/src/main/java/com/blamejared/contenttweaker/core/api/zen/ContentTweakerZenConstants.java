package com.blamejared.contenttweaker.core.api.zen;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;

public final class ContentTweakerZenConstants {
    public static final String MAIN_PACKAGE = ContentTweakerConstants.MOD_ID;

    public static final String RT_PACKAGE = MAIN_PACKAGE + "._rt";
    public static final String OBJECT_PACKAGE = MAIN_PACKAGE + ".object";
    public static final String FACTORY_PACKAGE = MAIN_PACKAGE + ".factory";

    public static final String NATIVE_PACKAGE = RT_PACKAGE + ".natives";
    public static final String UNKNOWN_CLASS_MARKER = RT_PACKAGE + ".Unknown";
    public static final String UNKNOWN_FACTORY_CLASS_MARKER = RT_PACKAGE + ".UnknownFactory";
    public static final String UNKNOWN_REFERENCE_CLASS_MARKER = RT_PACKAGE + ".UnknownReference";

    private ContentTweakerZenConstants() {}
}
