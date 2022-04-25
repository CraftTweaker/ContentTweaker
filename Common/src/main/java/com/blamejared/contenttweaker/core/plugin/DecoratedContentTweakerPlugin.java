package com.blamejared.contenttweaker.core.plugin;

import com.blamejared.contenttweaker.core.api.plugin.ContentTweakerPluginProvider;
import com.blamejared.contenttweaker.core.api.plugin.FactoryMappingRegistration;
import com.blamejared.contenttweaker.core.api.plugin.ObjectTypeRegistration;
import net.minecraft.resources.ResourceLocation;

record DecoratedContentTweakerPlugin(ResourceLocation id, ContentTweakerPluginProvider wrapped) implements ContentTweakerPluginProvider {

    @Override
    public void registerObjectTypes(final ObjectTypeRegistration registration) {
        this.wrapped().registerObjectTypes(registration);
    }

    @Override
    public void registerFactoryMappings(final FactoryMappingRegistration registration) {
        this.wrapped().registerFactoryMappings(registration);
    }

    @Override
    public String toString() {
        return this.id().toString();
    }
}