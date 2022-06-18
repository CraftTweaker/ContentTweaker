package com.blamejared.contenttweaker.vanilla.api.action.material;

import com.blamejared.contenttweaker.core.api.action.ContentTweakerAction;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public final class CreateMaterialAction implements ContentTweakerAction {
    private final ResourceLocation name;
    private final Runnable creator;

    private CreateMaterialAction(final ResourceLocation name, final Runnable creator) {
        this.name = name;
        this.creator = creator;
    }

    public static CreateMaterialAction of(final ResourceLocation name, final Runnable creator) {
        return new CreateMaterialAction(Objects.requireNonNull(name), Objects.requireNonNull(creator));
    }

    @Override
    public void apply() {
        this.creator.run();
    }

    @Override
    public String describe() {
        return "Creating material " + this.name;
    }
}
