package com.blamejared.contenttweaker.core.resource;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

final class RuntimePackResources implements PackResources {
    private final RuntimePack pack;

    RuntimePackResources(final RuntimePack pack) {
        this.pack = Objects.requireNonNull(pack);
    }

    @Nullable
    @Override
    public InputStream getRootResource(final String s) throws IOException {
        return this.pack.rootResource(s);
    }

    @Override
    public InputStream getResource(final PackType packType, final ResourceLocation resourceLocation) throws IOException {
        return this.pack.resource(packType, resourceLocation);
    }

    @Override
    public Collection<ResourceLocation> getResources(final PackType packType, final String s, final String s1, final int i, final Predicate<String> predicate) {
        return this.pack.resources(packType, s, s1, i, predicate);
    }

    @Override
    public boolean hasResource(final PackType packType, final ResourceLocation resourceLocation) {
        return this.pack.knowsResource(packType, resourceLocation);
    }

    @Override
    public Set<String> getNamespaces(final PackType packType) {
        return this.pack.namespaces(packType);
    }

    @Nullable
    @Override
    public <T> T getMetadataSection(final MetadataSectionSerializer<T> metadataSectionSerializer) throws IOException {
        return this.pack.metadataSection(metadataSectionSerializer);
    }

    @Override
    public String getName() {
        return this.pack.name();
    }

    @Override
    public void close() {
        // Do not close resources: it gets managed automatically on shutdown
    }
}
