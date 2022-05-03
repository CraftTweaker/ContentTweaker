package com.blamejared.contenttweaker.core.registry;

import com.blamejared.contenttweaker.core.api.object.ObjectHolder;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.registry.RegistryButler;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

import java.util.Collection;

public final class Winston implements RegistryButler {
    private final Multimap<ObjectType<?>, ObjectHolder<?>> commands;

    private Winston() {
        this.commands = LinkedHashMultimap.create();
    }

    public static Winston of() {
        return new Winston();
    }

    @Override
    public <T> void register(final ObjectHolder<T> holder) {
        this.commands.put(holder.type(), holder);
    }

    public <T> void executeForRegistry(final GameRegistry<T> registry) {
        this.commandsFor(registry.type()).forEach(registry::register);
    }

    private <T> Collection<ObjectHolder<T>> commandsFor(final ObjectType<T> type) {
        return this.commands.get(type)
                .stream()
                .map(GenericUtil::<ObjectHolder<T>>uncheck)
                .toList();
    }

    @Override
    public String toString() {
        return "Winston, at your service";
    }
}
