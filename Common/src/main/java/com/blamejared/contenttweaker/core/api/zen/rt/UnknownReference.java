package com.blamejared.contenttweaker.core.api.zen.rt;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.zen.object.Reference;
import com.blamejared.contenttweaker.core.api.zen.ContentTweakerZenConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name(ContentTweakerZenConstants.UNKNOWN_REFERENCE_CLASS_MARKER)
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class UnknownReference<T> extends Reference<T> {
    private UnknownReference(final ObjectType<T> type, final ResourceLocation id) {
        super(type, id);
    }

    public static <T> UnknownReference<T> of(final ObjectType<T> type, final ResourceLocation id) {
        return new UnknownReference<>(type, id);
    }
}
