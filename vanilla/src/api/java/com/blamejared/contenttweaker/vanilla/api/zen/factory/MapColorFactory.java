package com.blamejared.contenttweaker.vanilla.api.zen.factory;

import com.blamejared.contenttweaker.core.api.object.ObjectFactory;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;
import net.minecraft.world.level.material.MapColor;

public final class MapColorFactory implements ObjectFactory<MapColor> {
    public MapColorFactory() {}

    @Override
    public ObjectType<MapColor> type() {
        return VanillaObjectTypes.MAP_COLOR;
    }
}
