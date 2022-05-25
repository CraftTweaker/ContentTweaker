package com.blamejared.contenttweaker.vanilla.api.zen;

import com.blamejared.contenttweaker.core.api.zen.ContentTweakerZenConstants;

public final class ContentTweakerVanillaConstants {
    public static final String VANILLA_PACKAGE_MARKER = ".vanilla";

    public static final String VANILLA_BUILDER_PACKAGE = ContentTweakerZenConstants.MAIN_PACKAGE + ".builder" + VANILLA_PACKAGE_MARKER;
    public static final String VANILLA_FACTORY_PACKAGE = ContentTweakerZenConstants.FACTORY_PACKAGE + VANILLA_PACKAGE_MARKER;
    public static final String VANILLA_NATIVE_PACKAGE = ContentTweakerZenConstants.NATIVE_PACKAGE + VANILLA_PACKAGE_MARKER;
    public static final String VANILLA_OBJECT_PACKAGE = ContentTweakerZenConstants.OBJECT_PACKAGE + VANILLA_PACKAGE_MARKER;
    public static final String VANILLA_RT_PACKAGE = ContentTweakerZenConstants.RT_PACKAGE + VANILLA_PACKAGE_MARKER;
    public static final String VANILLA_UTIL_PACKAGE = ContentTweakerZenConstants.UTIL_PACKAGE + VANILLA_PACKAGE_MARKER;

    public static final String ITEM_BUILDER_PACKAGE = VANILLA_BUILDER_PACKAGE + ".item";

    public static final String BLOCK_TEXTURE_TEMPLATE = "missing_block.png";
    public static final String ITEM_TEXTURE_TEMPLATE = "missing_item.png";
}
