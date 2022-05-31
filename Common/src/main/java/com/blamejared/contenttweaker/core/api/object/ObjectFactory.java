package com.blamejared.contenttweaker.core.api.object;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.zen.ContentTweakerZenConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name(ContentTweakerZenConstants.RT_PACKAGE + ".Factory")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public interface ObjectFactory<T> {
    ObjectType<T> type();
}
