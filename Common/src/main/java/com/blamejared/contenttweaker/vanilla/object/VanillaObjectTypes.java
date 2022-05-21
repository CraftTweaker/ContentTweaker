package com.blamejared.contenttweaker.vanilla.object;

import com.blamejared.contenttweaker.core.api.object.ObjectType;
import net.minecraft.core.Registry;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public final class VanillaObjectTypes {
    public static ObjectType<Block> BLOCK = ObjectType.of(Registry.BLOCK_REGISTRY, Block.class);
    public static ObjectType<Item> ITEM = ObjectType.of(Registry.ITEM_REGISTRY, Item.class);
    public static ObjectType<SoundEvent> SOUND_EVENT = ObjectType.of(Registry.SOUND_EVENT_REGISTRY, SoundEvent.class);

    private VanillaObjectTypes() {}
}
