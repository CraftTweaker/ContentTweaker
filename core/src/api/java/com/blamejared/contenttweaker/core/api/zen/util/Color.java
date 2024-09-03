package com.blamejared.contenttweaker.core.api.zen.util;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.zen.ContentTweakerZenConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

/**
 * <p> A unified Color class </p>
 *
 * Int values are treated as components ranging from 0 to 255 unless otherwise specified.
 * Packed values contain a single color within the 32 bits of the int. Generally, packed values are represented using hexadecimal notation.
 */
@ZenCodeType.Name(ContentTweakerZenConstants.UTIL_PACKAGE + ".Color")
@Document("mods/ContentTweaker/util/Color")
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

    @ZenCodeType.Method
    public static Color packedArgb(final int color) {
        return packedRgba((color >>> 24) | ((color & 0xFFFFFF) << 8));
    }

    /**
     * Constructs a color out of integer components ranging from 0 to 255. The example below constructs a purple like color.
     *
     * The hex representation for this color is 0xc846c8
     *
     * @param r The red component
     * @param g The green component
     * @param b The blue component
     * @param a The alpha component
     *
     * @docParam r 200
     * @docParam g 70
     * @docParam b 200
     * @docParam a 255
     *
     * @return A new Color instance holding a purple color.
     */
    @ZenCodeType.Method("rgba")
    public static Color rgba(final int r, final int g, final int b, final int a) {
        return packedRgba(((r & 0xFF) << 24) | ((g & 0xFF) << 16) | ((b & 0xFF) << 8) | (a & 0xFF));
    }

    @ZenCodeType.Method("rgb")
    public static Color rgb(final int r, final int g, final int b) {
        return rgba(r, g, b, 0xFF);
    }

    @ZenCodeType.Method
    public static Color argb(final int a, final int r, final int g, final int b) {
        return rgba(r, g, b, a);
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

    @ZenCodeType.Method("asPackedRgba")
    public int asPackedRgba() {
        return this.color;
    }

    @ZenCodeType.Method("asPackedRgb")
    public int asPackedRgb() {
        return this.color >>> 8;
    }

    @ZenCodeType.Method("asPackedArgb")
    public int asPackedArgb() {
        return ((this.color & 0xFF) << 24) | (this.color >>> 8);
    }
}
