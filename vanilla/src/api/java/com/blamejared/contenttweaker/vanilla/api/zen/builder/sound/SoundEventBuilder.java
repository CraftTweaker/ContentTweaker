package com.blamejared.contenttweaker.vanilla.api.zen.builder.sound;

import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.object.ObjectHolder;
import com.blamejared.contenttweaker.core.api.resource.ResourceFragment;
import com.blamejared.contenttweaker.core.api.resource.ResourceManager;
import com.blamejared.contenttweaker.core.api.resource.StandardResourceFragmentKeys;
import com.blamejared.contenttweaker.core.api.zen.object.SimpleReference;
import com.blamejared.contenttweaker.vanilla.api.resource.PathHelper;
import com.blamejared.contenttweaker.vanilla.api.resource.SoundDefinition;
import com.blamejared.contenttweaker.vanilla.api.zen.ContentTweakerVanillaConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;

@ZenCodeType.Name(ContentTweakerVanillaConstants.SOUND_BUILDER_PACKAGE + ".SoundEventBuilder")
@ZenRegister(loaders = ContentTweakerConstants.CONTENT_LOADER_ID)
public abstract class SoundEventBuilder<T extends SoundEventBuilder<T>> {
    protected record GenerateFlags(boolean generateSound) {}

    private final String id;
    private final BiFunction<ObjectHolder<? extends SoundEvent>, Consumer<ResourceManager>, SimpleReference<SoundEvent>> registrationHandler;

    private boolean exampleSound;

    protected SoundEventBuilder(
            final String id,
            final BiFunction<ObjectHolder<? extends SoundEvent>, Consumer<ResourceManager>, SimpleReference<SoundEvent>> registrationHandler
    ) {
        this.id = Objects.requireNonNull(id);
        this.registrationHandler = Objects.requireNonNull(registrationHandler);
        this.exampleSound = true;
    }

    @ZenCodeType.Method("noExampleSound")
    public T noExampleSound() {
        this.exampleSound = false;
        return this.self();
    }

    @ZenCodeType.Method("build")
    public final SimpleReference<SoundEvent> build(@ZenCodeType.Optional @ZenCodeType.Nullable final String name) {
        final ResourceLocation soundId = ContentTweakerConstants.rl(this.id);
        final ResourceLocation registryId = name == null? soundId : ContentTweakerConstants.rl(name);
        final GenerateFlags flags = this.flags();

        return this.registrationHandler.apply(this.create(soundId, registryId, flags), manager -> this.createResources(soundId, registryId, manager, flags));
    }

    protected abstract ObjectHolder<? extends SoundEvent> create(final ResourceLocation id, final ResourceLocation name, final GenerateFlags flags);

    protected abstract void createResources(final ResourceLocation id, final ResourceLocation name, final ResourceManager manager, final GenerateFlags flags);

    protected final void generateJsonDefinition(final ResourceLocation soundId, final ResourceManager manager) {
        Objects.requireNonNull(soundId);
        Objects.requireNonNull(manager);

        final ResourceFragment assets = manager.fragment(StandardResourceFragmentKeys.CONTENT_TWEAKER_ASSETS);
        assets.provideOrAlter("sounds.json", SoundDefinition::of, it -> this.makeDefinition(it, soundId), SoundDefinition.SERIALIZER);
    }

    protected final void generateExampleSound(final ResourceLocation soundId, final GenerateFlags flags, final ResourceManager manager) {
        Objects.requireNonNull(soundId);
        Objects.requireNonNull(flags);
        Objects.requireNonNull(manager);

        if (!flags.generateSound()) {
            return;
        }

        final ResourceFragment assets = manager.fragment(StandardResourceFragmentKeys.CONTENT_TWEAKER_ASSETS);
        assets.provideTemplated(PathHelper.sound(soundId), "sound/what_is.ogg");
    }

    private SoundDefinition makeDefinition(final SoundDefinition definition, final ResourceLocation id) {
        return definition.add(id).sound(id).finish().finish();
    }

    private GenerateFlags flags() {
        return new GenerateFlags(this.exampleSound);
    }

    private T self() {
        return GenericUtil.uncheck(this);
    }
}
