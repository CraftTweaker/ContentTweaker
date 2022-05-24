package com.blamejared.contenttweaker.core.api.zen.rt;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.object.ObjectFactory;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.zen.ContentTweakerZenConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;

@ZenCodeType.Name(ContentTweakerZenConstants.UNKNOWN_FACTORY_CLASS_MARKER)
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class UnknownFactory implements ObjectFactory<Unknown> {
    private final ObjectType<?> type;

    private UnknownFactory(final ObjectType<?> type) {
        this.type = Objects.requireNonNull(type);
    }

    public static <T> UnknownFactory of(final ObjectType<T> type) {
        return new UnknownFactory(type);
    }

    @Override
    public ObjectType<Unknown> type() {
        return GenericUtil.uncheck(this.type);
    }
}