package com.blamejared.contenttweaker.vanilla.api.zen.factory;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.object.ObjectFactory;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.builder.sound.SoundTypeBuilder;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.world.level.block.SoundType;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name(ContentTweakerVanillaConstants.VANILLA_FACTORY_PACKAGE + ".SoundTypeFactory")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class SoundTypeFactory implements ObjectFactory<SoundType> {
    public SoundTypeFactory() {}

    @ZenCodeType.Method("type")
    public SoundTypeBuilder builder() {
        return SoundTypeBuilder.of();
    }

    @Override
    public ObjectType<SoundType> type() {
        return VanillaObjectTypes.SOUND_TYPE;
    }
}
