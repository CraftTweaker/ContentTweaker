package com.teamacronymcoders.contenttweaker.modules.forestry;

import com.teamacronymcoders.contenttweaker.modules.forestry.bee.BeeBranchRepresentation;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;

@ZenRegister
@ZenClass("mods.contenttweaker.ForestryFactory")
@ModOnly("forestry")
public class ForestryFactory {
    public static BeeBranchRepresentation createBeeBranch(String name) {
        return new BeeBranchRepresentation(name);
    }
}
