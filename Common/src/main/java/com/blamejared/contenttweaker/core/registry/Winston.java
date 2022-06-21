package com.blamejared.contenttweaker.core.registry;

import com.blamejared.contenttweaker.core.ContentTweakerCore;
import com.blamejared.contenttweaker.core.api.object.ObjectHolder;
import com.blamejared.contenttweaker.core.api.registry.RegistryButler;

import java.util.Objects;

// Handles registration of the various objects
public final class Winston implements RegistryButler {
    private Winston() {}

    public static Winston of() {
        return new Winston();
    }

    @Override
    public <T> void register(final ObjectHolder<T> holder) {
        ContentTweakerCore.core()
                .metaRegistry()
                .registryResolvers()
                .findResolverFor(Objects.requireNonNull(holder).type())
                .enqueueRegistration(holder);
    }

    @Override
    public String toString() {
        return "Winston, at your service";
    }
}
