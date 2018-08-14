package com.teamacronymcoders.contenttweaker.modules.tinkers.traits;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenClass("mods.contenttweaker.tconstruct.TraitDataRepresentation")
@ZenRegister
@ModOnly("tconstruct")
public class TConTraitDataRepresentation {
    private final ModifierNBT.IntegerNBT data;

    public TConTraitDataRepresentation(ModifierNBT.IntegerNBT data) {
        this.data = data;
    }

    @ZenGetter("color")
    public int getColor() {
        return data.color;
    }

    @ZenSetter("color")
    public void setColor(int color) {
        data.color = color;
    }

    @ZenGetter("current")
    public int getCurrent() {
        return data.current;
    }

    @ZenSetter("current")
    public void setCurrent(int current) {
        data.current = current;
    }

    @ZenGetter("extraInfo")
    public String getExtraInfo() {
        return data.extraInfo;
    }

    @ZenSetter("extraInfo")
    public void setExtraInfo(String extraInfo) {
        data.extraInfo = extraInfo;
    }

    @ZenGetter("identifier")
    public String getIdentifier() {
        return data.identifier;
    }

    @ZenSetter("identifier")
    public void setIdentifier(String identifier) {
        data.identifier = identifier;
    }

    @ZenGetter("level")
    public int getLevel() {
        return data.level;
    }

    @ZenSetter("level")
    public void setLevel(int level) {
        data.level = level;
    }

    @ZenGetter("max")
    public int getMax() {
        return data.max;
    }

    @ZenSetter("max")
    public void setMax(int max) {
        data.max = max;
    }

    @ZenGetter("info")
    @ZenMethod
    public String calcInfo() {
        return data.calcInfo();
    }

    @ZenGetter("colorString")
    @ZenMethod
    public String getColorString() {
        return data.getColorString();
    }
}
