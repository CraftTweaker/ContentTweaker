package com.teamacronymcoders.contenttweaker.modules.vanilla.utils.commands;

import com.teamacronymcoders.contenttweaker.api.ctobjects.world.IWorld;
import crafttweaker.api.player.IPlayer;
import crafttweaker.mc1120.player.MCPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.contenttweaker.Commands")
public class Commands {
    @ZenMethod
    public static void call(String command, IPlayer player, IWorld world) {
        if (!world.isRemote() && player instanceof MCPlayer) {
            EntityPlayer entityPlayer = ((MCPlayer) player).getInternal();
            CommandSenderWrapper commandSenderWrapper = new CommandSenderWrapper(entityPlayer, true, true);
            MinecraftServer server = world.getInternal().getMinecraftServer();
            if (server != null) {
                server.getCommandManager().executeCommand(commandSenderWrapper, command);
            }
        }
    }
}
