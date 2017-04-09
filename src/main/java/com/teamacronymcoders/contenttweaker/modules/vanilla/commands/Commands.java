package com.teamacronymcoders.contenttweaker.modules.vanilla.commands;

import com.teamacronymcoders.contenttweaker.ContentTweaker;
import com.teamacronymcoders.contenttweaker.api.wrappers.world.IWorld;
import minetweaker.api.player.IPlayer;
import minetweaker.mc1102.player.MCPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.server.FMLServerHandler;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.contenttweaker.Commands")
public class Commands {
    @ZenMethod
    public static void call(String command, IPlayer player, IWorld world) {
        if (!world.isRemote() && player instanceof MCPlayer && world.getInternal() instanceof World) {
            EntityPlayer entityPlayer = ((MCPlayer) player).getInternal();
            CommandSenderWrapper commandSenderWrapper = new CommandSenderWrapper(entityPlayer, true, true);
            MinecraftServer server = ((World) world.getInternal()).getMinecraftServer();
            if (server != null) {
                server.getCommandManager().executeCommand(commandSenderWrapper, command);
            }
        }
    }
}
