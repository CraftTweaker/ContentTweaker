package com.blamejared.contenttweaker.blocks.types.machine.gui;

import net.minecraft.client.*;

@FunctionalInterface
public interface RenderCallback {
    void render(Minecraft minecraft, int mouseX, int mouseY, float partialTicks);
}
