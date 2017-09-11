package com.teamacronymcoders.contenttweaker.api.utils;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.mc1120.commands.CraftTweakerCommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

import java.util.Collection;

import static crafttweaker.mc1120.commands.SpecialMessagesChat.*;

public class ResourceListCommand<T> extends CraftTweakerCommand {
    private ResourceList<T> resourceList;

    public ResourceListCommand(String subCommandName, ResourceList<T> resourceList) {
        super(subCommandName);
        this.resourceList = resourceList;
    }

    @Override
    protected void init() {
        setDescription(getClickableCommandText("\u00A72/ct " + subCommandName, "/ct " + subCommandName, true),
                getNormalMessage(" \u00A73Outputs a list of all " + subCommandName + "names in the game to the crafttweaker.log"));
    }

    @Override
    public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
        CraftTweakerAPI.logCommand(subCommandName + ":");
        Collection<String> names = resourceList.getAllNames();
        for (String name : names) {
            CraftTweakerAPI.logCommand("<" + subCommandName + ":" + name + ">");
        }
        sender.sendMessage(getLinkToCraftTweakerLog("List of " + subCommandName + " generated;", sender));
    }
}
