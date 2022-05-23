package com.blamejared.contenttweaker.core.resource;

import com.blamejared.contenttweaker.core.ContentTweakerCore;
import com.blamejared.contenttweaker.core.api.resource.ResourceFragment;
import com.blamejared.contenttweaker.core.api.resource.ResourceManager;
import com.blamejared.contenttweaker.core.api.resource.ResourceTemplateHelper;
import net.minecraft.server.packs.PackType;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class RuntimeResourceManager implements ResourceManager {
    private record Cleaner(Supplier<? extends Collection<? extends AutoCloseable>> closeables) implements Runnable {
        @Override
        public void run() {
            this.closeables.get().forEach(this::tryClose);
        }

        private <T extends AutoCloseable> void tryClose(final T t) {
            try {
                t.close();
            } catch (final Exception e) {
                ContentTweakerCore.LOGGER.warn("An error occurred while trying to close resource " + t, e);
            }
        }
    }

    private final TemplateHelper templateHelper;
    private final Map<ResourceFragment.Key, RuntimeFragment> fragments;

    private RuntimeResourceManager() {
        this.templateHelper = TemplateHelper.of();
        this.fragments = new HashMap<>();
        Runtime.getRuntime().addShutdownHook(new Thread(new Cleaner(this.fragments::values)));
    }

    public static RuntimeResourceManager of() {
        return new RuntimeResourceManager();
    }

    @Override
    public ResourceFragment fragment(final ResourceFragment.Key key) {
        Objects.requireNonNull(key);
        return this.fragments.computeIfAbsent(key, RuntimeFragment::of);
    }

    @Override
    public ResourceTemplateHelper templateHelper() {
        return this.templateHelper;
    }

    Map<String, RuntimeFragment> fragments(final PackType type) {
        return this.fragments.values()
                .stream()
                .filter(it -> it.key().type() == type)
                .collect(Collectors.toMap(it -> it.key().id(), Function.identity()));
    }
}
