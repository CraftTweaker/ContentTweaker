package com.blamejared.contenttweaker.core.api.registry;

import com.blamejared.contenttweaker.core.api.ContentTweakerApi;
import com.blamejared.contenttweaker.core.api.object.ObjectHolder;

public interface RegistryButler {
    static RegistryButler get() {
        return ContentTweakerApi.get().registryButler();
    }

    <T> void register(final ObjectHolder<T> holder);
}
