package com.teamacronymcoders.contenttweaker.modules.vanilla.commands;

import com.teamacronymcoders.contenttweaker.ContentTweaker;
import minetweaker.api.player.IPlayer;
import minetweaker.mc1102.player.MCPlayer;
import net.minecraft.entity.player.EntityPlayer;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.contenttweaker.Commands")
public class Commands {
    @ZenMethod
    public static void call(String command, IPlayer player) {
        if (player instanceof MCPlayer) {
            EntityPlayer entityPlayer = ((MCPlayer) player).getInternal();
            ContentTweaker.proxy.callCommand(command, entityPlayer);
        }
    }
}
