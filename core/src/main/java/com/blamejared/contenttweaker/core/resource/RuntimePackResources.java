package com.blamejared.contenttweaker.core.resource;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.server.packs.resources.IoSupplier;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Set;

final class RuntimePackResources implements PackResources {
    private final RuntimePack pack;

    RuntimePackResources(final RuntimePack pack) {
        this.pack = Objects.requireNonNull(pack);
    }

    @Nullable
    @Override
    public IoSupplier<InputStream> getRootResource(final String... strings) {
        return this.pack.rootResource(strings);
    }

    @Override
    public IoSupplier<InputStream> getResource(final PackType packType, final ResourceLocation resourceLocation) {
        return this.pack.resource(packType, resourceLocation);
    }

    @Override
    public void listResources(final PackType packType, final String s, final String s1, final ResourceOutput resourceOutput) {
        this.pack.resources(packType, s, s1, resourceOutput);
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
    public String packId() {
        return this.pack.packId();
    }

    @Override
    public boolean isBuiltin() {
        return true;
    }

    @Override
    public void close() {
        // Do not close resources: it gets managed automatically on shutdown
    }
}
