package com.blamejared.contenttweaker.vanilla.service;

import com.blamejared.contenttweaker.vanilla.api.zen.object.property.StandardBlockProperties;
import com.blamejared.contenttweaker.vanilla.mixin.BlockBehaviorAccessor;
import com.blamejared.contenttweaker.vanilla.mixin.BlockBehaviorPropertiesAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import java.util.function.Function;
import java.util.function.ToIntFunction;

final class BlockPropertiesVanillaAdapterFactory {
    private record BlockPropertiesVanillaAdapter(BlockBehaviorPropertiesAccessor accessor) implements StandardBlockProperties.VanillaAdapter {
        @Override
        public Material material() {
            return this.accessor().contenttweaker$material();
        }

        @Override
        public Function<BlockState, MaterialColor> materialColor() {
            return this.accessor().contenttweaker$materialColor();
        }

        @Override
        public boolean hasCollision() {
            return this.accessor().contenttweaker$hasCollision();
        }

        @Override
        public SoundType soundType() {
            return this.accessor().contenttweaker$soundType();
        }

        @Override
        public ToIntFunction<BlockState> lightEmission() {
            return this.accessor().contenttweaker$lightEmission();
        }

        @Override
        public boolean requiresCorrectToolForDrops() {
            return this.accessor().contenttweaker$requiresCorrectToolForDrops();
        }

        @Override
        public boolean isRandomlyTicking() {
            return this.accessor().contenttweaker$isRandomlyTicking();
        }

        @Override
        public boolean canOcclude() {
            return this.accessor().contenttweaker$canOcclude();
        }

        @Override
        public boolean isAir() {
            return this.accessor().contenttweaker$isAir();
        }
    }

    StandardBlockProperties.VanillaAdapter of(final Block block) {
        return new BlockPropertiesVanillaAdapter((BlockBehaviorPropertiesAccessor) ((BlockBehaviorAccessor) block).contenttweaker$properties());
    }
}
