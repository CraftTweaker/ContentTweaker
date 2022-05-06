package com.blamejared.contenttweaker.core.api.zen.object;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.zen.ContentTweakerZenConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;

@ZenCodeType.Name(ContentTweakerZenConstants.OBJECT_PACKAGE + ".Reference")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public abstract class Reference<T> { // Designed for extension by stuff like ItemReference
    private final ObjectType<T> type;
    private final ResourceLocation id;

    protected Reference(final ObjectType<T> type, final ResourceLocation id) {
        this.type = type;
        this.id = id;
    }

    @ZenCodeType.Getter("registryId")
    public final ResourceLocation registryId() {
        return this.type().id().location();
    }

    @ZenCodeType.Getter("id")
    public final ResourceLocation id() {
        return this.id;
    }

    public final ObjectType<T> type() {
        return this.type;
    }

    public final T get() {
        return GenericUtil.uncheck(Objects.requireNonNull(Registry.REGISTRY.get(this.registryId())).get(this.id()));
    }
}
