package com.blamejared.contenttweaker.forge.registry;

import com.blamejared.contenttweaker.core.api.registry.GameRegistry;
import net.minecraft.Util;

import java.util.List;

public interface DeferredGameRegistry<T> extends GameRegistry<T> {
    List<Runnable> commands();

    default void doRegistration() {
        Util.make(this.commands(), commands -> {
            commands.forEach(Runnable::run);
            commands.clear();
        });
    }
}
