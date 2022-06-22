package com.blamejared.contenttweaker.vanilla.service;

import com.blamejared.contenttweaker.vanilla.api.VanillaBridge;
import com.blamejared.contenttweaker.vanilla.api.zen.object.property.StandardBlockProperties;
import com.blamejared.contenttweaker.vanilla.mixin.BlockBehaviorPropertiesAccessor;
import com.blamejared.contenttweaker.vanilla.mixin.CreativeModeTabAccessor;
import com.google.common.base.Suppliers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public final class TheOtherSide implements VanillaBridge {
    private final Supplier<BlockPropertiesVanillaAdapterFactory> propertiesVanillaAdapterFactory;

    public TheOtherSide() {
        this.propertiesVanillaAdapterFactory = Suppliers.memoize(BlockPropertiesVanillaAdapterFactory::new);
    }

    @Override
    public StandardBlockProperties.VanillaAdapter blockPropertiesAdapterOf(final Block block) {
        return this.propertiesVanillaAdapterFactory.get().of(block);
    }

    @Override
    public void blockPropertiesDrops(final BlockBehaviour.Properties properties, final ResourceLocation drops) {
        ((BlockBehaviorPropertiesAccessor) properties).contenttweaker$drops(drops);
    }

    @Override
    public String creativeTabId(final CreativeModeTab tab) {
        return ((CreativeModeTabAccessor) tab).contenttweaker$langId();
    }

    @Override
    public void creativeTabs(final CreativeModeTab[] tabs) {
        CreativeModeTabAccessor.contenttweaker$tabs(tabs);
    }
}
