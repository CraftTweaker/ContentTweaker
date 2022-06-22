package com.blamejared.contenttweaker.vanilla.api;

import com.blamejared.contenttweaker.vanilla.api.zen.object.property.StandardBlockProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public interface VanillaBridge {
    StandardBlockProperties.VanillaAdapter blockPropertiesAdapterOf(final Block block);
    void blockPropertiesDrops(final BlockBehaviour.Properties properties, final ResourceLocation drops);

    String creativeTabId(final CreativeModeTab tab);
    void creativeTabs(final CreativeModeTab[] tabs);
}
