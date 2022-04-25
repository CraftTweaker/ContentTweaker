package com.blamejared.contenttweaker.core.api.object;

import com.blamejared.contenttweaker.core.zen.ContentTweakerZenConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name(ContentTweakerZenConstants.RT_PACKAGE + ".Factory")
@ZenRegister
public interface ObjectFactory<T> {
    ObjectType<T> type();
}
