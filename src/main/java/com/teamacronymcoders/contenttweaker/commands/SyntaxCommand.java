package com.teamacronymcoders.contenttweaker.commands;

import com.teamacronymcoders.contenttweaker.ContentTweaker;
import crafttweaker.mc1120.commands.CraftTweakerCommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static crafttweaker.mc1120.commands.SpecialMessagesChat.getClickableCommandText;
import static crafttweaker.mc1120.commands.SpecialMessagesChat.getLinkToCraftTweakerLog;
import static crafttweaker.mc1120.commands.SpecialMessagesChat.getNormalMessage;

public class SyntaxCommand extends CraftTweakerCommand {
    public SyntaxCommand() {
        super("cot-scripts");
    }

    @Override
    protected void init() {
        setDescription(getClickableCommandText("\u00A72/ct syntax", "/ct syntax", true), getNormalMessage(" \u00A73Checks the Syntax of the scripts"), getNormalMessage(" \u00A73To see the effect you have to restart the game"), getNormalMessage(" \u00A73Will print errors of the Bracket Handler"));
    }

    @Override
    public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
        if(args.length > 0 && args[0].equals("debug")) {
            ContentTweaker.instance.getScriptHandler().enableDebug();
        }

        sender.sendMessage(getNormalMessage("\u00A7bBeginning load of the scripts"));
        boolean loadSuccessful = ContentTweaker.instance.getScriptHandler().loadScript(false);

        if(loadSuccessful) {
            sender.sendMessage(getNormalMessage("Syntax of scripts is \u00A7acorrect\u00A7r, to see the effect \u00A7erestart the game"));
            sender.sendMessage(getNormalMessage("Please be advised that \u00A7bbrackets (<>) \u00A7rmay have \u00A74errored, see above."));
            sender.sendMessage(getNormalMessage("If no errors appeared above everything was fine."));
        } else {
            sender.sendMessage(getLinkToCraftTweakerLog("\u00A74Syntax of the scripts is incorrect!", sender));
        }
    }

    @Override
    public List<String> getSubSubCommand(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        List<String> commands = new ArrayList<>(1);
        commands.add("debug");
        return commands;
    }
}
