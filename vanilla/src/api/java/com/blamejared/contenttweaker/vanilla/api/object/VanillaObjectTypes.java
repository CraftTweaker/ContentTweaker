package com.blamejared.contenttweaker.vanilla.api.object;

import com.blamejared.contenttweaker.core.api.object.ObjectType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

public final class VanillaObjectTypes {
    public static ObjectType<Block> BLOCK = ObjectType.of(Registries.BLOCK, Block.class);
    public static ObjectType<CreativeModeTab> CREATIVE_TAB = ObjectType.of(Registries.CREATIVE_MODE_TAB, CreativeModeTab.class);
    public static ObjectType<Item> ITEM = ObjectType.of(Registries.ITEM, Item.class);
    public static ObjectType<MapColor> MAP_COLOR = ObjectType.of(new ResourceLocation("map_color"), MapColor.class);
    public static ObjectType<SoundEvent> SOUND_EVENT = ObjectType.of(Registries.SOUND_EVENT, SoundEvent.class);
    public static ObjectType<SoundType> SOUND_TYPE = ObjectType.of(new ResourceLocation("sound_type"), SoundType.class);
    public static ObjectType<Tier> TIER = ObjectType.of(new ResourceLocation("tier"), Tier.class);

    private VanillaObjectTypes() {}
}
