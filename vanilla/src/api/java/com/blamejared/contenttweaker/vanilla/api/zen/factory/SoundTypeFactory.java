package com.blamejared.contenttweaker.vanilla.api.zen.factory;

import com.blamejared.contenttweaker.core.api.ContentTweakerApi;
import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.action.RegisterObjectAction;
import com.blamejared.contenttweaker.core.api.object.ObjectFactory;
import com.blamejared.contenttweaker.core.api.object.ObjectHolder;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.zen.object.Reference;
import com.blamejared.contenttweaker.core.api.zen.object.SimpleReference;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;
import com.blamejared.contenttweaker.vanilla.api.util.ReferencingSoundType;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.SoundType;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;

@ZenCodeType.Name(ContentTweakerVanillaConstants.VANILLA_FACTORY_PACKAGE + ".SoundTypeFactory")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class SoundTypeFactory implements ObjectFactory<SoundType> {
    public SoundTypeFactory() {}

    @ZenCodeType.Method("create")
    public SimpleReference<SoundType> create(
            final String name,
            final float volume,
            final float pitch,
            final Reference<SoundEvent> breakSound,
            final Reference<SoundEvent> stepSound,
            final Reference<SoundEvent> placeSound,
            final Reference<SoundEvent> hitSound,
            final Reference<SoundEvent> fallSound
    ) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(breakSound);
        Objects.requireNonNull(stepSound);
        Objects.requireNonNull(placeSound);
        Objects.requireNonNull(hitSound);
        Objects.requireNonNull(fallSound);
        final ResourceLocation id = ContentTweakerConstants.rl(name);
        final ObjectHolder<SoundType> holder = ObjectHolder.of(
                VanillaObjectTypes.SOUND_TYPE,
                id,
                () -> ReferencingSoundType.of(volume, pitch, breakSound, stepSound, placeSound, hitSound, fallSound)
        );
        ContentTweakerApi.apply(RegisterObjectAction.of(holder));
        return SimpleReference.of(VanillaObjectTypes.SOUND_TYPE, id);
    }

    @Override
    public ObjectType<SoundType> type() {
        return VanillaObjectTypes.SOUND_TYPE;
    }
}
