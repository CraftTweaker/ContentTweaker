package com.blamejared.contenttweaker.forge.resource;

import com.blamejared.contenttweaker.core.resource.RuntimeRepositorySource;
import com.blamejared.contenttweaker.core.resource.UserRepositorySource;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.function.Function;

public final class ForgeResourceManager {
    private ForgeResourceManager() {}

    public static void init(final IEventBus modBus) {
        modBus.addListener(EventPriority.HIGH, false, AddPackFindersEvent.class, ForgeResourceManager::injectRuntime);
        modBus.addListener(EventPriority.LOW, false, AddPackFindersEvent.class, ForgeResourceManager::injectUser);
    }

    private static void injectRuntime(final AddPackFindersEvent event) {
        injectSource(event, RuntimeRepositorySource::of);
    }

    private static void injectUser(final AddPackFindersEvent event) {
        injectSource(event, UserRepositorySource::of);
    }

    private static void injectSource(final AddPackFindersEvent event, final Function<PackType, ? extends RepositorySource> constructor) {
        event.addRepositorySource(constructor.apply(event.getPackType()));
    }
}
