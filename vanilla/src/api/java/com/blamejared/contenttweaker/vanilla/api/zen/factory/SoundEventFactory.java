package com.blamejared.contenttweaker.vanilla.api.zen.factory;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.object.ObjectFactory;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.builder.sound.SoundEventBuilder;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.sounds.SoundEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenCodeType.Name(ContentTweakerVanillaConstants.VANILLA_FACTORY_PACKAGE + ".SoundEventFactory")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class SoundEventFactory implements ObjectFactory<SoundEvent> {
    public SoundEventFactory() {}

    @Override
    public ObjectType<SoundEvent> type() {
        return VanillaObjectTypes.SOUND_EVENT;
    }

    @ZenCodeType.Method("event")
    public SoundEventBuilder event(final String id) {
        return SoundEventBuilder.of(id);
    }
}
