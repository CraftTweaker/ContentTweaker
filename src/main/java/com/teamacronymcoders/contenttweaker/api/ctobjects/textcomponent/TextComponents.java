package com.teamacronymcoders.contenttweaker.api.ctobjects.textcomponent;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.contenttweaker.TextComponents")
public class TextComponents {

    @ZenMethod
    public MCTextComponent text(String text){
        return new MCTextComponent(text);
    }

    @ZenMethod
    public MCTextComponentTranslation textTranslation(String text){
        return new MCTextComponentTranslation(text);
    }

}
