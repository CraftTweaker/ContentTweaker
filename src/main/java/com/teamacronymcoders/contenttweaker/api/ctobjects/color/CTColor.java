package com.teamacronymcoders.contenttweaker.api.ctobjects.color;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.contenttweaker.Color")
public class CTColor {
    private int color;

    private CTColor(int color) {
        this.color = color;
    }

    @ZenMethod
    public static CTColor fromHex(String text) {
        if (text.length() == 6) {
            text = "FF" + text;
        }
        return new CTColor((int)Long.parseLong(text, 16));
    }

    @ZenMethod
    public static CTColor fromEpeen(String text) {
        int thickShaft = (int) (text.chars().filter(ch -> ch == '=').count()) * 32;
        int thinShaft = (int) (text.chars().filter(ch -> ch == '-').count()) * 32;
        int juice = (int) (text.chars().filter(ch -> ch == '~').count()) * 32;

        String hex = "";
        hex += thickShaft == 0 ? "00" : Integer.toHexString(thickShaft);
        hex += thinShaft == 0 ? "00" : Integer.toHexString(thinShaft);
        hex += juice == 0 ? "00" : Integer.toHexString(juice);
        return fromHex(hex);
    }

    @ZenMethod
    public static CTColor fromInt(int number) {
        return fromHex(Integer.toHexString(number));
    }

    @ZenMethod
    public int getIntColor() {
        return this.color;
    }
}
