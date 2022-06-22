package com.blamejared.contenttweaker.core.registry;

import com.blamejared.contenttweaker.core.api.object.ObjectFactory;
import com.blamejared.contenttweaker.core.api.object.ObjectFactoryMapping;
import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.object.ReferenceFactory;
import com.blamejared.contenttweaker.core.api.object.RegistryResolver;
import com.blamejared.contenttweaker.core.api.registry.ContentTweakerRegistry;
import com.blamejared.contenttweaker.core.api.zen.object.Reference;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public final class ContentTweakerRegistryWrapper implements ContentTweakerRegistry {
    private final MetaRegistry registry;

    private ContentTweakerRegistryWrapper(final MetaRegistry registry) {
        this.registry = registry;
    }

    public static ContentTweakerRegistryWrapper of(final MetaRegistry registry) {
        return new ContentTweakerRegistryWrapper(Objects.requireNonNull(registry));
    }

    @Override
    public <T> ObjectType<T> findType(final ResourceLocation id, final Class<T> type) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(type);
        final ObjectType<T> possible = this.registry.objectTypes().get(id);
        if (possible == null) {
            return null;
        }
        if (type != possible.type()) {
            throw new IllegalStateException("Found type " + id + " but looked up type does not match");
        }
        return possible;
    }

    @Override
    public <T, U extends ObjectFactory<T>> ObjectFactoryMapping<T, U> findObjectFactory(final ObjectType<T> type, final Class<U> factoryType) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(factoryType);
        final ObjectFactoryMapping<T, U> candidate = this.registry.factoryMappings().findMappingFor(type);
        if (candidate == null) {
            return null;
        }
        if (candidate.type() != factoryType) {
            throw new IllegalStateException("Found factory for " + type + " but built type does not match");
        }
        return candidate;
    }

    @Override
    public <T, U extends Reference<T>> ReferenceFactory<T, U> findReferenceFactory(final ObjectType<T> type, final Class<U> referenceType) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(referenceType);
        final ReferenceFactory<T, U> candidate = this.registry.referenceFactories().findFactoryFor(type);
        if (candidate == null) {
            return null;
        }
        if (candidate.type().getRawType() != referenceType) {
            throw new IllegalStateException("Found factory for " + type + " but raw built type does not match");
        }
        return candidate;
    }

    @Override
    public <T> RegistryResolver<T> findResolver(final ObjectType<T> type) {
        Objects.requireNonNull(type);
        return this.registry.registryResolvers().findResolverFor(type);
    }
}
