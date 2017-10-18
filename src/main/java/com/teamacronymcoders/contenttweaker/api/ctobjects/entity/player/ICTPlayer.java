package com.teamacronymcoders.contenttweaker.api.ctobjects.entity.player;

import com.teamacronymcoders.contenttweaker.api.ctobjects.entity.living.IEntityLiving;
import com.teamacronymcoders.contenttweaker.api.ctobjects.enums.Hand;
import com.teamacronymcoders.contenttweaker.api.ctobjects.mutableitemstack.IMutableItemStack;
import crafttweaker.api.player.IPlayer;
import net.minecraft.entity.player.EntityPlayer;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.contenttweaker.Player")
public interface ICTPlayer extends IPlayer, IEntityLiving<EntityPlayer> {
    @ZenMethod
    IMutableItemStack getHeldItem(Hand hand);
}
