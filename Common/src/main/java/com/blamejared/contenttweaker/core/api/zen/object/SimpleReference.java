package com.blamejared.contenttweaker.core.api.zen.object;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.zen.ContentTweakerZenConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;

@ZenCodeType.Name(ContentTweakerZenConstants.OBJECT_PACKAGE + ".SimpleReference")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class SimpleReference<T> extends Reference<T> {
    private SimpleReference(final ObjectType<T> type, final ResourceLocation id) {
        super(type, id);
    }

    public static <T> SimpleReference<T> of(final ObjectType<T> type, final ResourceLocation id) {
        return new SimpleReference<>(Objects.requireNonNull(type), Objects.requireNonNull(id));
    }
}
