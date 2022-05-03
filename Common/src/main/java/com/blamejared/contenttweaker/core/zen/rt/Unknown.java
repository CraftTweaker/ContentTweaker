package com.blamejared.contenttweaker.core.zen.rt;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.object.ObjectFactory;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.zen.ContentTweakerZenConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name(ContentTweakerZenConstants.UNKNOWN_CLASS_MARKER)
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class Unknown {
    @ZenCodeType.Name(ContentTweakerZenConstants.UNKNOWN_FACTORY_CLASS_MARKER)
    @ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
    public static final class Factory implements ObjectFactory<Unknown> {
        public static final Factory INSTANCE = new Factory();

        private Factory() {}

        @Override
        public ObjectType<Unknown> type() {
            return null; // Stub
        }
    }

    private Unknown() {}
}
