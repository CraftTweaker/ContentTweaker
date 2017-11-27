package com.teamacronymcoders.contenttweaker.api.ctobjects.entity.player;

import com.teamacronymcoders.contenttweaker.api.ctobjects.enums.Hand;
import com.teamacronymcoders.contenttweaker.api.ctobjects.mutableitemstack.IMutableItemStack;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.contenttweaker.Player")
public interface ICTPlayer extends IPlayer {
    @ZenMethod
    IMutableItemStack getHeldItem(Hand hand);
}
