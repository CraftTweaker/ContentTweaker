package com.blamejared.contenttweaker.vanilla.api.zen.builder.sound;

import com.blamejared.contenttweaker.core.api.ContentTweakerApi;
import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.action.RegisterObjectAction;
import com.blamejared.contenttweaker.core.api.object.ObjectHolder;
import com.blamejared.contenttweaker.core.api.resource.ResourceFragment;
import com.blamejared.contenttweaker.core.api.resource.ResourceManager;
import com.blamejared.contenttweaker.core.api.resource.StandardResourceFragmentKeys;
import com.blamejared.contenttweaker.core.api.zen.object.SimpleReference;
import com.blamejared.contenttweaker.vanilla.api.resource.PathHelper;
import com.blamejared.contenttweaker.vanilla.api.resource.SoundDefinition;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.contenttweaker.vanilla.api.object.VanillaObjectTypes;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.util.NameUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;
import java.util.function.BiConsumer;

@ZenCodeType.Name(ContentTweakerVanillaConstants.SOUND_BUILDER_PACKAGE + ".SoundEventBuilder")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public final class SoundEventBuilder {
    private final String id;
    private boolean exampleSet;

    private SoundEventBuilder(final String id) {
        this.id = id;
        this.exampleSet = true;
    }

    public static SoundEventBuilder of(final String id) {
        return new SoundEventBuilder(Objects.requireNonNull(id));
    }

    @ZenCodeType.Method("build")
    public SimpleReference<SoundEvent> build(@ZenCodeType.Optional @ZenCodeType.Nullable final String name) {
        final ResourceLocation soundId = ContentTweakerConstants.rl(NameUtil.fixing(this.id));
        final ResourceLocation registryId = ContentTweakerConstants.rl(name == null? this.id : name);
        final ObjectHolder<SoundEvent> eventHolder = ObjectHolder.of(VanillaObjectTypes.SOUND_EVENT, registryId, () -> new SoundEvent(soundId));
        ContentTweakerApi.apply(RegisterObjectAction.of(eventHolder, manager -> this.generateResources(soundId, this.exampleSet, manager)));
        return SimpleReference.of(VanillaObjectTypes.SOUND_EVENT, registryId);
    }

    private void generateResources(final ResourceLocation soundId, final boolean exampleSet, final ResourceManager manager) {
        final BiConsumer<ResourceLocation, ResourceManager> generator = exampleSet? this::generateExampleResources : this::generateSpecifiedResources;
        generator.accept(soundId, manager);
    }

    private void generateExampleResources(final ResourceLocation soundId, final ResourceManager manager) {
        final ResourceFragment assets = manager.fragment(StandardResourceFragmentKeys.CONTENT_TWEAKER_ASSETS);
        assets.provideOrAlter("sounds.json", SoundDefinition::of, it -> this.makeDefinition(it, soundId), SoundDefinition.SERIALIZER);
        assets.provideTemplated(PathHelper.sound(soundId), "sound/what_is.ogg");
    }

    private void generateSpecifiedResources(final ResourceLocation soundId, final ResourceManager manager) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private SoundDefinition makeDefinition(final SoundDefinition definition, final ResourceLocation id) {
        return definition.add(id).sound(id).finish().finish();
    }
}
