package com.blamejared.contenttweaker.vanilla.api.zen.builder.sound;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.object.ObjectHolder;
import com.blamejared.contenttweaker.core.api.resource.ResourceManager;
import com.blamejared.contenttweaker.core.api.zen.object.SimpleReference;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.BiFunction;
import java.util.function.Consumer;

@ZenCodeType.Name(ContentTweakerVanillaConstants.SOUND_BUILDER_PACKAGE + ".FixedRangeEvent")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class FixedRangeSoundEventBuilder extends SoundEventBuilder<FixedRangeSoundEventBuilder> {
    private Float range;

    public FixedRangeSoundEventBuilder(
            final String id,
            final BiFunction<ObjectHolder<? extends SoundEvent>, Consumer<ResourceManager>, SimpleReference<SoundEvent>> registrationHandler
    ) {
        super(id, registrationHandler);
        this.range = null;
    }

    @ZenCodeType.Method("range")
    public FixedRangeSoundEventBuilder range(final float range) {
        if (range < 0.0F) {
            throw new IllegalArgumentException("Range cannot be negative");
        }
        this.range = range;
        return this;
    }

    @Override
    protected ObjectHolder<? extends SoundEvent> create(final ResourceLocation id, final ResourceLocation name, final GenerateFlags flags) {
        if (this.range == null) {
            throw new IllegalStateException("Unable to create a sound with a fixed range without the range");
        }
        return ObjectHolder.of(VanillaObjectTypes.SOUND_EVENT, name, () -> SoundEvent.createFixedRangeEvent(id, this.range));
    }

    @Override
    protected void createResources(final ResourceLocation id, final ResourceLocation name, final ResourceManager manager, final GenerateFlags flags) {
        this.generateJsonDefinition(id, manager);
        this.generateExampleSound(id, flags, manager);
    }
}
