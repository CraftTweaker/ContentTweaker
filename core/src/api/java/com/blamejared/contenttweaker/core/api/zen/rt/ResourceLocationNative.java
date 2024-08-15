package com.blamejared.contenttweaker.core.api.zen.rt;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.zen.ContentTweakerZenConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;

/**
 * Exposes {@link ResourceLocation} to the ContentTweaker loader.
 */
@NativeTypeRegistration(value = ResourceLocation.class, zenCodeName = ResourceLocationNative.CLASS_NAME)
@SuppressWarnings("unused")
@Document("mods/ContentTweaker/resource/ResourceLocation")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class ResourceLocationNative {
    public static final String CLASS_NAME = ContentTweakerZenConstants.MAIN_PACKAGE + ".resource.ResourceLocation";

    private ResourceLocationNative() {}

    /**
     * Construct a ResourceLocation
     *
     * @param namespace The namespace to target. Namespaces are generally modid's
     * @param path The path within the namespace to target
     * @return A new ResourceLocation
     *
     * @docParam namespace minecraft
     * @docParam path dirt
     */
    @ZenCodeType.StaticExpansionMethod("of")
    public static ResourceLocation of(final String namespace, final String path) {
        return new ResourceLocation(namespace, path); // TODO("Panic on ResLocException")
    }

    @ZenCodeType.Getter("namespace")
    public static String namespace(final ResourceLocation $this) {
        return $this.getNamespace();
    }

    @ZenCodeType.Getter("path")
    public static String path(final ResourceLocation $this) {
        return $this.getPath();
    }

    @ZenCodeType.Operator(ZenCodeType.OperatorType.EQUALS)
    public static boolean is(final ResourceLocation $this, final ResourceLocation other) {
        return Objects.equals($this, other);
    }

    @ZenCodeType.Caster(implicit = true)
    public static String asString(final ResourceLocation $this) {
        return $this.toString();
    }
}
