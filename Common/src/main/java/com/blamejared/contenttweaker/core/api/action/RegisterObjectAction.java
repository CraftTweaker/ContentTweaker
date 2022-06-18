package com.blamejared.contenttweaker.core.api.action;

import com.blamejared.contenttweaker.core.api.object.ObjectHolder;
import com.blamejared.contenttweaker.core.api.registry.RegistryButler;
import com.blamejared.contenttweaker.core.api.resource.ResourceManager;

import java.util.Objects;
import java.util.function.Consumer;

// TODO("Find a way to make non-registry backed work: maybe ask Winston? Or rewire GameRegistry and rework resolvers to resolve to it?")
public final class RegisterObjectAction<T> implements ContentTweakerAction {
    private final ObjectHolder<T> holder;
    private final Consumer<ResourceManager> resourceProvider;

    private RegisterObjectAction(final ObjectHolder<T> holder, final Consumer<ResourceManager> resourceProvider) {
        this.holder = holder;
        this.resourceProvider = resourceProvider;
    }

    public static <T> RegisterObjectAction<T> of(final ObjectHolder<T> holder, final Consumer<ResourceManager> resourceProvider) {
        return new RegisterObjectAction<>(Objects.requireNonNull(holder), Objects.requireNonNull(resourceProvider));
    }

    @Override
    public void apply() {
        RegistryButler.get().register(this.holder);
        this.resourceProvider.accept(ResourceManager.get());
    }

    @Override
    public String describe() {
        return "Registering object of type %s with name %s".formatted(this.holder.type(), this.holder.id());
    }
}
