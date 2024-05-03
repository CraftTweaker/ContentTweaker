package com.blamejared.contenttweaker.vanilla.api.zen.factory;

import com.blamejared.contenttweaker.core.api.object.ObjectFactory;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;
import net.minecraft.world.level.material.MaterialColor;

public final class MaterialColorFactory implements ObjectFactory<MaterialColor> {
    public MaterialColorFactory() {
        throw new IllegalStateException("New material colors cannot be created");
    }

    @Override
    public ObjectType<MaterialColor> type() {
        return VanillaObjectTypes.MATERIAL_COLOR;
    }
}
