package com.teamacronymcoders.contenttweaker.api.utils;

import com.google.common.collect.Lists;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.mc1120.commands.CraftTweakerCommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

import java.util.List;
import java.util.Set;

import static crafttweaker.mc1120.commands.SpecialMessagesChat.*;

public class ResourceListCommand extends CraftTweakerCommand {
    private List<String> resourceList;

    public ResourceListCommand(String subCommandName, Set<String> resourceList) {
        this(subCommandName, Lists.newArrayList(resourceList));
    }


    public ResourceListCommand(String subCommandName, List<String> resourceList) {
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
        for (String name : resourceList) {
            CraftTweakerAPI.logCommand("<" + subCommandName + ":" + name + ">");
        }
        sender.sendMessage(getLinkToCraftTweakerLog("List of " + subCommandName + " generated;", sender));
    }
}
