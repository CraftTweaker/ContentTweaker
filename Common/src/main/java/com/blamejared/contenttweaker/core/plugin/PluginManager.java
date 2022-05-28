package com.blamejared.contenttweaker.core.plugin;

import com.blamejared.contenttweaker.core.ContentTweakerCore;
import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.api.plugin.ContentTweakerPlugin;
import com.blamejared.contenttweaker.core.api.plugin.ContentTweakerPluginProvider;
import com.blamejared.contenttweaker.core.registry.MetaRegistry;
import com.blamejared.contenttweaker.core.registry.ObjectTypeRegistry;
import com.blamejared.contenttweaker.core.util.FreezableList;
import com.blamejared.contenttweaker.core.util.NonApiCraftTweakerWrapper;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.plugin.IBracketParserRegistrationHandler;
import com.google.common.base.Suppliers;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ResourceLocationException;
import net.minecraft.resources.ResourceLocation;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class PluginManager {
    private final Supplier<List<? extends ContentTweakerPluginProvider>> providers;

    private PluginManager() {
        this.providers = Suppliers.memoize(this::buildPluginList);
    }

    public static PluginManager of() {
        return new PluginManager();
    }

    public void initializePlugins(final MetaRegistry metaRegistry) {
        final ObjectTypeRegistry objectTypeRegistry = metaRegistry.objectTypes();
        objectTypeRegistry.registerTypes(ObjectTypeRegistrationManager.get(this.each(ContentTweakerPluginProvider::registerObjectTypes)));
        metaRegistry.factoryMappings().registerMappings(objectTypeRegistry, FactoryMappingRegistrationManager.get(this.each(ContentTweakerPluginProvider::registerFactoryMappings)));
        metaRegistry.referenceFactories().registerFactories(objectTypeRegistry, ReferenceFactoryRegistrationManager.get(this.each(ContentTweakerPluginProvider::registerReferenceFactories)));
    }

    public void registerPluginBrackets(final IBracketParserRegistrationHandler handler) {
        this.each(ContentTweakerPluginProvider::registerCustomBrackets)
                .accept((name, parser, dumperData) -> handler.registerParserFor(ContentTweakerConstants.CONTENT_LOADER_ID, name, parser, dumperData));
    }

    private List<? extends ContentTweakerPluginProvider> buildPluginList() {
        return FreezableList.of(this.discoverPlugins(), true);
    }

    private List<? extends ContentTweakerPluginProvider> discoverPlugins() {
        return NonApiCraftTweakerWrapper.findClassesWithAnnotation(ContentTweakerPlugin.class)
                .map(this::checkAndCast)
                .map(this::initPlugin)
                .filter(Objects::nonNull)
                .toList();
    }

    @SuppressWarnings("unchecked")
    private Pair<ResourceLocation, Class<? extends ContentTweakerPluginProvider>> checkAndCast(final Class<?> clazz) {
        if(!ContentTweakerPluginProvider.class.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException("Invalid plugin class annotated with @ContentTweakerPlugin: it must implement ContentTweakerPluginProvider");
        }

        final ResourceLocation id;
        try {
            final String targetId = Objects.requireNonNull(clazz.getAnnotation(ContentTweakerPlugin.class)).value();
            id = new ResourceLocation(targetId);
            if(id.getNamespace().equals("minecraft")) {
                throw new ResourceLocationException("Illegal namespace 'minecraft'");
            }
        } catch(final ResourceLocationException e) {
            throw new IllegalArgumentException("Invalid plugin class ID: not a valid resource location", e);
        }

        return Pair.of(id, (Class<? extends ContentTweakerPluginProvider>) clazz);
    }

    private DecoratedContentTweakerPlugin initPlugin(final Pair<ResourceLocation, Class<? extends ContentTweakerPluginProvider>> data) {
        try {
            final ResourceLocation id = data.getFirst();
            final ContentTweakerPluginProvider provider = data.getSecond().getConstructor().newInstance();
            ContentTweakerCore.LOGGER.info("Successfully identified and loaded plugin {}", id);
            CraftTweakerAPI.LOGGER.info("CoT: Successfully identified and loaded ContentTweaker plugin {}", id);
            return new DecoratedContentTweakerPlugin(id, provider);
        } catch(final InstantiationException | NoSuchMethodException | IllegalAccessException |
                      InvocationTargetException e) {
            ContentTweakerCore.LOGGER.error("Unable to load plugin class '" + data.getSecond()
                    .getName() + "' due to an error", e);
            return null;
        }
    }

    private <T> Consumer<T> each(final BiConsumer<ContentTweakerPluginProvider, T> consumer) {
        Objects.requireNonNull(consumer);
        return t -> this.providers.get().forEach(o -> consumer.accept(o, t));
    }
}
