package com.blamejared.contenttweaker.vanilla.api.zen.factory;

import com.blamejared.contenttweaker.core.api.ContentTweakerApi;
import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.action.RegisterObjectAction;
import com.blamejared.contenttweaker.core.api.object.ObjectFactory;
import com.blamejared.contenttweaker.core.api.object.ObjectHolder;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.resource.ResourceManager;
import com.blamejared.contenttweaker.core.api.util.ClassArchitect;
import com.blamejared.contenttweaker.core.api.zen.object.SimpleReference;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.zen.builder.sound.SoundEventBuilder;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.sounds.SoundEvent;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.BiFunction;
import java.util.function.Consumer;

@ZenCodeType.Name(ContentTweakerVanillaConstants.VANILLA_FACTORY_PACKAGE + ".SoundEventFactory")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class SoundEventFactory implements ObjectFactory<SoundEvent> {
    private static final ClassArchitect<SoundEventBuilder<?>> ARCHITECT = ClassArchitect.of(String.class, BiFunction.class);
    private static final BiFunction<ObjectHolder<? extends SoundEvent>, Consumer<ResourceManager>, SimpleReference<SoundEvent>> REGISTER = SoundEventFactory::register;

    public SoundEventFactory() {}

    private static <T extends SoundEvent> SimpleReference<SoundEvent> register(final ObjectHolder<T> holder, final Consumer<ResourceManager> resourceProvider) {
        ContentTweakerApi.apply(RegisterObjectAction.of(holder, resourceProvider));
        return SimpleReference.of(VanillaObjectTypes.SOUND_EVENT, holder.id());
    }

    @Override
    public ObjectType<SoundEvent> type() {
        return VanillaObjectTypes.SOUND_EVENT;
    }

    @ZenCodeType.Method("typed")
    public <T extends SoundEventBuilder<T>> T event(final Class<T> reifiedT, final String id) {
        return ARCHITECT.construct(reifiedT, id, REGISTER);
    }
}
