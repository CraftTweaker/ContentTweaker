package com.blamejared.contenttweaker.core.zen.rt;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.zen.ContentTweakerZenConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;

@NativeTypeRegistration(value = ResourceLocation.class, zenCodeName = ResourceLocationNative.CLASS_NAME)
@SuppressWarnings("unused")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class ResourceLocationNative {
    public static final String CLASS_NAME = ContentTweakerZenConstants.MAIN_PACKAGE + ".resource.ResourceLocation";

    private ResourceLocationNative() {}

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
