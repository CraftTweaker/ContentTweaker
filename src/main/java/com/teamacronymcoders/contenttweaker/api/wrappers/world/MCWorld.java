package com.teamacronymcoders.contenttweaker.api.wrappers.world;

import net.minecraft.world.World;

public class MCWorld implements IWorld {
    private World world;

    public MCWorld(World world) {
        this.world = world;
    }

    @Override
    public boolean isRemote() {
        return world.isRemote;
    }
}
