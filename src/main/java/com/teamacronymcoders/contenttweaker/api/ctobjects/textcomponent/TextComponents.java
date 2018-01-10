package com.teamacronymcoders.contenttweaker.api.ctobjects.textcomponent;

import net.minecraft.advancements.CriteriaTriggers;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.contenttweaker.TextComponents")
public class TextComponents {

    @ZenMethod
    public static MCTextComponent text(String text){
        return new MCTextComponent(text);
    }

    @ZenMethod
    public static MCTextComponentTranslation textTranslation(String text){
        return new MCTextComponentTranslation(text);
    }
}
