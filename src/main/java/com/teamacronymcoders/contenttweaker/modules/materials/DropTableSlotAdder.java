package com.teamacronymcoders.contenttweaker.modules.materials;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenExpansion("mods.contenttweaker.DropTableBuilder")
@ZenRegister
public class DropTableSlotAdder {
    @ZenMethod
    public static CTDropTableBuilder newSlot(CTDropTableBuilder builder) {
        return new CTDropTableBuilder(builder.toString());
    }
}
