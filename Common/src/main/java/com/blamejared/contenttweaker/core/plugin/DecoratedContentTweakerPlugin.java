package com.blamejared.contenttweaker.core.plugin;

import com.blamejared.contenttweaker.core.api.plugin.ContentTweakerPluginProvider;
import com.blamejared.contenttweaker.core.api.plugin.CustomBracketRegistration;
import com.blamejared.contenttweaker.core.api.plugin.FactoryMappingRegistration;
import com.blamejared.contenttweaker.core.api.plugin.ObjectTypeRegistration;
import com.blamejared.contenttweaker.core.api.plugin.ReferenceFactoryRegistration;
import com.blamejared.contenttweaker.core.api.plugin.ResolverRegistration;
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
    public void registerReferenceFactories(final ReferenceFactoryRegistration registration) {
        this.wrapped().registerReferenceFactories(registration);
    }

    @Override
    public void registerResolvers(final ResolverRegistration registration) {
        this.wrapped().registerResolvers(registration);
    }

    @Override
    public void registerCustomBrackets(final CustomBracketRegistration registration) {
        this.wrapped().registerCustomBrackets(registration);
    }

    @Override
    public String toString() {
        return this.id().toString();
    }
}
