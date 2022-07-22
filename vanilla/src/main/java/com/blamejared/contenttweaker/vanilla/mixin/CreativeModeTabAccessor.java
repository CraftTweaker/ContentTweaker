package com.blamejared.contenttweaker.vanilla.mixin;

import net.minecraft.world.item.CreativeModeTab;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CreativeModeTab.class)
public interface CreativeModeTabAccessor {
    @Accessor("langId")
    String contenttweaker$langId();

    @Accessor("TABS")
    @Mutable
    static void contenttweaker$tabs(final CreativeModeTab[] tabs) {
        throw new UnsupportedOperationException("Mixin not applied");
    }
}
