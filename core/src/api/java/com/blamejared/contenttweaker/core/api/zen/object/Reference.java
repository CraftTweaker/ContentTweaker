package com.blamejared.contenttweaker.core.api.zen.object;

import com.blamejared.contenttweaker.core.api.ContentTweakerApi;
import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.zen.ContentTweakerZenConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;
import java.util.Optional;

/**
 * <p> A Reference is an object that points to another object through its type and id. <p>
 *
 * <p> ContentTweaker operates at a point in time where registry objects may or may not be registered. Thus, in order to access existing
 * objects, a bridge is needed. References are used in ContentTweaker scripts and resolved afterward.<p>
 *
 * <p> The exhaustive list of available references can be obtained using <code>/ct dump references</code>. </p>
 *
 * <p> The following are valid References:
 * <ul>
 *     <li>&lt;reference:item:minecraft:diamond&gt;</li>
 *     <li>&lt;reference:minecraft:item:minecraft:diamond&gt; - A more verbose but highly specialized bracket</li>
 *     <li>&lt;reference:block:hahamod:hello&gt; - Whether this block exists or not will be checked later</li>
 * </ul>
 *
 * </p>
 */
@ZenCodeType.Name(ContentTweakerZenConstants.OBJECT_PACKAGE + ".Reference")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
@Document("mods/ContentTweaker/vanilla/object/Reference")
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
            this.resolved = ContentTweakerApi.get().registry().findResolver(this.type()).resolve(this.id());
        }
        if (this.resolved == null) {
            throw new IllegalStateException("Cannot resolve object at this time in %s".formatted(this));
        }
        return this.resolved;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final Reference<?> that = GenericUtil.uncheck(obj);
        return Objects.equals(this.type(), that.type()) && Objects.equals(this.id(), that.id());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id(), this.type());
    }

    @Override
    public String toString() {
        return "Ref(%s/%s)".formatted(this.id(), this.type());
    }
}
