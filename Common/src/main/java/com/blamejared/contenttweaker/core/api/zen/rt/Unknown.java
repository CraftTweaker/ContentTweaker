package com.blamejared.contenttweaker.core.api.zen.rt;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.zen.ContentTweakerZenConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name(ContentTweakerZenConstants.UNKNOWN_CLASS_MARKER)
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class Unknown {
    private Unknown() {}
}
