package com.teamacronymcoders.tailoredobjects.representations.tileentities;

public class TileEntityRepresentation {
    private String name;
    private boolean shouldTick;
    private String commandName;
    private TileEntityCommandRepresentation commands;

    public boolean isShouldTick() {
        return shouldTick;
    }

    public void setShouldTick(boolean shouldTick) {
        this.shouldTick = shouldTick;
    }

    public TileEntityCommandRepresentation getCommands() {
        return commands;
    }

    public void setCommands(TileEntityCommandRepresentation commands) {
        this.commands = commands;
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
