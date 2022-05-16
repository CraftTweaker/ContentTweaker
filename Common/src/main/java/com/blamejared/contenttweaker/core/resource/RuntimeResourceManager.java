package com.blamejared.contenttweaker.core.resource;

import com.blamejared.contenttweaker.core.api.resource.ResourceManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RuntimeResourceManager implements ResourceManager {
    private static final class Resources {
        private final Map<String, Map<String, byte[]>> resources;

        Resources() {
            this.resources = new HashMap<>();
        }

        void provide(final ResourceLocation id, final byte... resource) {
            this.provide(id.getNamespace(), id.getPath(), resource);
        }

        Map<String, Map<String, byte[]>> allResources() {
            return this.resources;
        }

        private void provide(final String namespace, final String path, final byte... resource) {
            final Map<String, byte[]> targets = this.resources.computeIfAbsent(namespace, it -> new HashMap<>());
            if (targets.containsKey(path)) {
                throw new IllegalStateException("Path " + path + " in " + namespace + " was already associated with a resource");
            }
            targets.put(path, resource);
        }
    }

    private final Resources[] resources;

    private RuntimeResourceManager() {
        this.resources = new Resources[] { new Resources(), new Resources() };
    }

    public static RuntimeResourceManager of() {
        return new RuntimeResourceManager();
    }

    @Override
    public void provide(final PackType type, final ResourceLocation id, final byte... resource) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(id);
        Objects.requireNonNull(resource);
        this.resources[type.ordinal()].provide(id, resource);
    }

    Map<String, Map<String, byte[]>> allResources(final PackType type) {
        return this.resources[type.ordinal()].allResources();
    }
}
