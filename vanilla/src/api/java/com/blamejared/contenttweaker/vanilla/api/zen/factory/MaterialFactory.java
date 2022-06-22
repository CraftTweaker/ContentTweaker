package com.blamejared.contenttweaker.vanilla.api.zen.factory;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.object.ObjectFactory;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.zen.object.Reference;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.builder.material.MaterialBuilder;
import com.blamejared.contenttweaker.vanilla.api.zen.builder.sound.SoundEventBuilder;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name(ContentTweakerVanillaConstants.VANILLA_FACTORY_PACKAGE + ".MaterialFactory")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class MaterialFactory implements ObjectFactory<Material> {
    public MaterialFactory() {}

    @Override
    public ObjectType<Material> type() {
        return VanillaObjectTypes.MATERIAL;
    }

    @ZenCodeType.Method("material")
    public MaterialBuilder material(final Reference<MaterialColor> color) {
        return MaterialBuilder.of(color);
    }
}
