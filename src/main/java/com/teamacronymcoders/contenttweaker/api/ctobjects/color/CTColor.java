package com.teamacronymcoders.contenttweaker.api.ctobjects.color;

import com.teamacronymcoders.base.util.Coloring;
import com.teamacronymcoders.contenttweaker.api.ICTObject;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.contenttweaker.Color")
public class CTColor implements ICTObject<Coloring> {
    private Coloring color;

    private CTColor(Coloring color) {
        this.color = color;
    }

    @ZenMethod
    public static CTColor fromHex(String text) {
        return new CTColor(Coloring.fromHex(text));
    }

    @ZenMethod
    public static CTColor fromEpeen(String text) {
        return new CTColor(Coloring.fromEpeen(text));
    }

    @ZenMethod
    public static CTColor fromInt(int number) {
        return new CTColor(Coloring.fromInt(number));
    }

    @ZenMethod
    public int getIntColor() {
        return this.color.getIntColor();
    }

    @Override
    public Coloring getInternal() {
        return this.color;
    }
}
