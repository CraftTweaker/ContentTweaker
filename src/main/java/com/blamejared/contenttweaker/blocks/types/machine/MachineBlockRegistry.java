package com.blamejared.contenttweaker.blocks.types.machine;

import com.blamejared.contenttweaker.blocks.types.machine.gui.*;
import net.minecraft.inventory.container.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;

import java.util.*;

public class MachineBlockRegistry {
    
    public static final List<CoTBlockTile> ALL_BLOCKS = new ArrayList<>();
    public static final Map<ResourceLocation, TileEntityType<CoTTile>> TILE_TYPES = new HashMap<>();
    public static final Map<ResourceLocation, ContainerType<CoTContainer>> CONTAINER_TYPES = new HashMap<>();
}
