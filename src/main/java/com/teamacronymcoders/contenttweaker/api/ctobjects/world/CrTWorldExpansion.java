package com.teamacronymcoders.contenttweaker.api.ctobjects.world;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.minecraft.CraftTweakerMC;
import stanhebben.zenscript.annotations.ZenCaster;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenExpansion("crafttweaker.world.IWorld")
@ZenRegister
public class CrTWorldExpansion {

    @ZenMethod
    @ZenCaster
    public static IWorld toContentTweakerWorld(crafttweaker.api.world.IWorld world) {
        return new MCWorld(CraftTweakerMC.getWorld(world));
    }
}
