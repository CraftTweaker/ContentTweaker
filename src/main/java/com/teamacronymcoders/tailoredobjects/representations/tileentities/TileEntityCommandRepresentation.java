package com.teamacronymcoders.tailoredobjects.representations.tileentities;

public class TileEntityCommandRepresentation {
    private String name;
    private String onTick;

    public String getOnTick() {
        return onTick;
    }

    public void setOnTick(String onTick) {
        this.onTick = onTick;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
