package com.teamacronymcoders.contenttweaker.modules.vanilla.functions;

import com.teamacronymcoders.contenttweaker.api.wrappers.world.IWorld;
import minetweaker.api.item.IItemStack;
import minetweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("mods.contenttweaker.IItemRightClick")
public interface IItemRightClick {
    String onRightClick(IItemStack itemStack, IWorld world, IPlayer player, String hand);
}
