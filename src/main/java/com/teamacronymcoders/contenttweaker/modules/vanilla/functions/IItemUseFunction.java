package com.teamacronymcoders.contenttweaker.modules.vanilla.functions;

import com.teamacronymcoders.contenttweaker.api.ctobjects.enums.ActionResult;
import com.teamacronymcoders.contenttweaker.api.ctobjects.enums.Facing;
import com.teamacronymcoders.contenttweaker.api.ctobjects.blockpos.IBlockPos;
import com.teamacronymcoders.contenttweaker.api.ctobjects.world.IWorld;
import crafttweaker.api.player.IPlayer;

public interface IItemUseFunction {
    ActionResult useItem(IPlayer player, IWorld world, IBlockPos pos, Hand hand, Facing facing, float hitX, float hitY, float hitZ);
}
