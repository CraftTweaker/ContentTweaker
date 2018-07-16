package com.teamacronymcoders.contenttweaker.modules.materials;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenExpansion("mods.contenttweaker.DropTableSlotBuilder")
@ZenRegister
public class DropTableSlotAdder {
    @ZenMethod
    public static DropTableSlotBuilder newSlot(DropTableSlotBuilder dt) {
        return new DropTableSlotBuilder(dt.toString());
    }
}
