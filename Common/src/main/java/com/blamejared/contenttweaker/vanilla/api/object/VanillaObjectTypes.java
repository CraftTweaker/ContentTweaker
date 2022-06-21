package com.blamejared.contenttweaker.vanilla.api.object;

import com.blamejared.contenttweaker.core.api.object.ObjectType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public final class VanillaObjectTypes {
    public static ObjectType<Block> BLOCK = ObjectType.of(Registry.BLOCK_REGISTRY, Block.class);
    public static ObjectType<CreativeModeTab> CREATIVE_TAB = ObjectType.of(new ResourceLocation("creative_tab"), CreativeModeTab.class);
    public static ObjectType<Item> ITEM = ObjectType.of(Registry.ITEM_REGISTRY, Item.class);
    public static ObjectType<Material> MATERIAL = ObjectType.of(new ResourceLocation("material"), Material.class);
    public static ObjectType<MaterialColor> MATERIAL_COLOR = ObjectType.of(new ResourceLocation("material_color"), MaterialColor.class);
    public static ObjectType<SoundEvent> SOUND_EVENT = ObjectType.of(Registry.SOUND_EVENT_REGISTRY, SoundEvent.class);
    public static ObjectType<SoundType> SOUND_TYPE = ObjectType.of(new ResourceLocation("sound_type"), SoundType.class);
    public static ObjectType<Tier> TIER = ObjectType.of(new ResourceLocation("tier"), Tier.class);

    private VanillaObjectTypes() {}
}
