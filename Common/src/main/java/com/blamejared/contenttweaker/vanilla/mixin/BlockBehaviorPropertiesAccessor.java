package com.blamejared.contenttweaker.vanilla.mixin;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.function.Function;
import java.util.function.ToIntFunction;

@Mixin(BlockBehaviour.Properties.class)
public interface BlockBehaviorPropertiesAccessor {
    @Accessor("material") Material contenttweaker$material();
    @Accessor("materialColor") Function<BlockState, MaterialColor> contenttweaker$materialColor();
    @Accessor("hasCollision") boolean contenttweaker$hasCollision();
    @Accessor("soundType") SoundType contenttweaker$soundType();
    @Accessor("lightEmission") ToIntFunction<BlockState> contenttweaker$lightEmission();

    @Accessor("requiresCorrectToolForDrops") boolean contenttweaker$requiresCorrectToolForDrops();
    @Accessor("isRandomlyTicking") boolean contenttweaker$isRandomlyTicking();

    @Accessor("canOcclude") boolean contenttweaker$canOcclude();
    @Accessor("isAir") boolean contenttweaker$isAir();


    @Accessor("drops") void contenttweaker$drops(final ResourceLocation drops);
}
