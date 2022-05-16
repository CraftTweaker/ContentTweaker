package com.blamejared.contenttweaker.core.api.resource;

import com.blamejared.contenttweaker.core.api.ContentTweakerApi;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;

import java.util.Objects;

public interface ResourceManager {
    static ResourceManager get() {
        return ContentTweakerApi.get().resourceManager();
    }

    void provide(final PackType type, final ResourceLocation id, final byte... resource);

    default <T> void provide(final PackType type, final ResourceLocation id, final T resource, final ResourceSerializer<? super T> serializer) {
        this.provide(type, id, Objects.requireNonNull(serializer).serialize(Objects.requireNonNull(resource)));
    }
}
