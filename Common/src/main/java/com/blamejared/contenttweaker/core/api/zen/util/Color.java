package com.blamejared.contenttweaker.core.api.zen.util;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.zen.ContentTweakerZenConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name(ContentTweakerZenConstants.UTIL_PACKAGE + ".Color")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class Color {
    private final int color;

    private Color(final int color) {
        this.color = color;
    }

    @ZenCodeType.Method("packedRgba")
    public static Color packedRgba(final int color) {
        return new Color(color);
    }

    @ZenCodeType.Method("packedRgb")
    public static Color packedRgb(final int color) {
        return packedRgba((color << 8) | 0xFF);
    }

    @ZenCodeType.Method("rgba")
    public static Color rgba(final int r, final int g, final int b, final int a) {
        return packedRgba(((r & 0xFF) << 24) | ((g & 0xFF) << 16) | ((b & 0xFF) << 8) | (a & 0xFF));
    }

    @ZenCodeType.Method("rgb")
    public static Color rgb(final int r, final int g, final int b) {
        return rgba(r, g, b, 0xFF);
    }

    @ZenCodeType.Getter("r")
    public int r() {
        return (this.color >> 24) & 0xFF;
    }

    @ZenCodeType.Getter("g")
    public int g() {
        return (this.color >> 16) & 0xFF;
    }

    @ZenCodeType.Getter("b")
    public int b() {
        return (this.color >> 8) & 0xFF;
    }

    @ZenCodeType.Getter("a")
    public int a() {
        return this.color & 0xFF;
    }
}
