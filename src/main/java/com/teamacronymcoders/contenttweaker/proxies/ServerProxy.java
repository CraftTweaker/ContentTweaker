package com.teamacronymcoders.contenttweaker.proxies;

import com.teamacronymcoders.contenttweaker.modules.vanilla.commands.CommandSenderWrapper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.server.FMLServerHandler;

@SideOnly(Side.SERVER)
public class ServerProxy extends CommonProxy {
    @Override
    public void createErrorSilencingLoader() {
        //No-op on Server
    }

    @Override
    public void callCommand(String command, EntityPlayer entityPlayer) {
        CommandSenderWrapper commandSenderWrapper = new CommandSenderWrapper(entityPlayer, false, true);
        MinecraftServer server = FMLServerHandler.instance().getServer();
        server.getCommandManager().executeCommand(commandSenderWrapper, command);
    }
}
