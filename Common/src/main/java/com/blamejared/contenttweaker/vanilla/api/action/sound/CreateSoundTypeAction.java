package com.blamejared.contenttweaker.vanilla.api.action.sound;

import com.blamejared.contenttweaker.core.api.action.ContentTweakerAction;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public final class CreateSoundTypeAction implements ContentTweakerAction {
    private final ResourceLocation name;
    private final Runnable creator;

    private CreateSoundTypeAction(final ResourceLocation name, final Runnable creator) {
        this.name = name;
        this.creator = creator;
    }

    public static CreateSoundTypeAction of(final ResourceLocation name, final Runnable creator) {
        return new CreateSoundTypeAction(Objects.requireNonNull(name), Objects.requireNonNull(creator));
    }

    @Override
    public void apply() {
        this.creator.run();
    }

    @Override
    public String describe() {
        return "Creating sound type " + this.name;
    }
}
