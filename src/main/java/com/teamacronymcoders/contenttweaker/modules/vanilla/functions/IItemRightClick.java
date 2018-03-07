package com.teamacronymcoders.contenttweaker.modules.vanilla.functions;

import com.teamacronymcoders.contenttweaker.api.ctobjects.entity.player.ICTPlayer;
import com.teamacronymcoders.contenttweaker.api.ctobjects.enums.Hand;
import com.teamacronymcoders.contenttweaker.api.ctobjects.mutableitemstack.IMutableItemStack;
import com.teamacronymcoders.contenttweaker.api.ctobjects.mutableitemstack.MCMutableItemStack;
import com.teamacronymcoders.contenttweaker.api.ctobjects.world.IWorld;
import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("mods.contenttweaker.IItemRightClick")
public interface IItemRightClick {
    String onRightClick(IMutableItemStack itemStack, IWorld world, ICTPlayer player, String hand);
}
