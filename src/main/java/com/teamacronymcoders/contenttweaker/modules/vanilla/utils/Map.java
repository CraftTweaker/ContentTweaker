package com.teamacronymcoders.contenttweaker.modules.vanilla.utils;

import com.teamacronymcoders.base.event.PlaceWaypointEvent;
import com.teamacronymcoders.contenttweaker.api.ctobjects.blockpos.IBlockPos;
import com.teamacronymcoders.contenttweaker.api.ctobjects.color.CTColor;
import com.teamacronymcoders.contenttweaker.api.ctobjects.world.IWorld;
import net.minecraftforge.common.MinecraftForge;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.contenttweaker.Map")
public class Map {
    @ZenMethod
    public static void setWayPoint(String name, IWorld world, IBlockPos pos, CTColor color) {
        MinecraftForge.EVENT_BUS.post(new PlaceWaypointEvent(name, world.getDimension(), pos.getInternal(), color.getIntColor()));
    }
}
