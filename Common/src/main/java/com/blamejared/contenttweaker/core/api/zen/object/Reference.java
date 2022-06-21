package com.blamejared.contenttweaker.core.api.zen.object;

import com.blamejared.contenttweaker.core.api.ContentTweakerApi;
import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.zen.ContentTweakerZenConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Optional;

@ZenCodeType.Name(ContentTweakerZenConstants.OBJECT_PACKAGE + ".Reference")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public abstract class Reference<T> { // Designed for extension by stuff like ItemReference
    private final ObjectType<T> type;
    private final ResourceLocation id;

    private T resolved; // TODO("Maybe allow this to get set back to null?")

    protected Reference(final ObjectType<T> type, final ResourceLocation id) {
        this.type = type;
        this.id = id;
        this.resolved = null;
    }

    @ZenCodeType.Getter("typeId")
    public final ResourceLocation typeId() {
        return this.type.id();
    }

    @Nullable
    @ZenCodeType.Getter("registryId")
    @ZenCodeType.Nullable
    public final ResourceLocation registryId() {
        return Optional.ofNullable(this.type().key()).map(ResourceKey::location).orElse(null);
    }

    @ZenCodeType.Getter("id")
    public final ResourceLocation id() {
        return this.id;
    }

    public final ObjectType<T> type() {
        return this.type;
    }

    public final T get() {
        if (this.resolved == null) {
            this.resolved = ContentTweakerApi.get().findResolver(this.type()).resolve(this.id());
        }
        if (this.resolved == null) {
            throw new IllegalStateException("Cannot resolve object at this time in %s".formatted(this));
        }
        return this.resolved;
    }

    @Override
    public String toString() {
        return "Ref(%s/%s)".formatted(this.id(), this.type());
    }
}
