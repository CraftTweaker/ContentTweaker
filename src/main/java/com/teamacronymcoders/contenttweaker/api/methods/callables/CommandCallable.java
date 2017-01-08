package com.teamacronymcoders.contenttweaker.api.methods.callables;

import com.teamacronymcoders.contenttweaker.CommandSenderWrapper;
import com.teamacronymcoders.contenttweaker.api.methods.MethodParameters;
import net.minecraft.command.ICommandManager;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class CommandCallable implements ICallableMethod {
    private String commandString;
    private CommandSenderWrapper commandSenderWrapper;
    private ICommandManager commandManager;

    public CommandCallable(String commandString) {
        this.commandString = commandString;
        this.commandManager = FMLCommonHandler.instance().getMinecraftServerInstance().getCommandManager();
    }

    @Override
    public void callMethod(MethodParameters parameters) {
        commandSenderWrapper = new CommandSenderWrapper(parameters.getEntity(), true, true);
        commandManager.executeCommand(commandSenderWrapper, commandString);
    }
}
