package com.blamejared.contenttweaker.vanilla.mixin;

import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BlockBehaviour.class)
public interface BlockBehaviorAccessor {
    @Accessor("properties") BlockBehaviour.Properties contenttweaker$properties();
}
