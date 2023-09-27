package com.blamejared.contenttweaker.core.resource;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import org.apache.commons.io.input.ReaderInputStream;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

final class UserPack implements PackResources {
    private static final String METADATA = "pack.mcmeta";

    private final Gson gson;
    private final String name;
    private final PackResources resources;
    private final JsonObject metadata;

    UserPack(final Gson gson, final String name, final PackResources resources, final JsonObject metadata) {
        this.gson = Objects.requireNonNull(gson);
        this.name = Objects.requireNonNull(name);
        this.resources = Objects.requireNonNull(resources);
        this.metadata = Objects.requireNonNull(metadata);
    }

    @Nullable
    @Override
    public InputStream getRootResource(final String s) throws IOException {
        if (METADATA.equals(s)) {
            return new ReaderInputStream(new StringReader(this.gson.toJson(this.metadata)), StandardCharsets.UTF_8);
        }
        return this.resources.getRootResource(s);
    }

    @Override
    public InputStream getResource(final PackType packType, final ResourceLocation resourceLocation) throws IOException {
        return this.resources.getResource(packType, resourceLocation);
    }

    @Override
    public Collection<ResourceLocation> getResources(final PackType packType, final String s, final String s1, final Predicate<ResourceLocation> predicate) {
        return this.resources.getResources(packType, s, s1, predicate);
    }

    @Override
    public boolean hasResource(final PackType packType, final ResourceLocation resourceLocation) {
        return this.resources.hasResource(packType, resourceLocation);
    }

    @Override
    public Set<String> getNamespaces(final PackType packType) {
        return this.resources.getNamespaces(packType);
    }

    @Nullable
    @Override
    public <T> T getMetadataSection(final MetadataSectionSerializer<T> metadataSectionSerializer) {
        final String name = metadataSectionSerializer.getMetadataSectionName();
        return this.metadata.has(name)? metadataSectionSerializer.fromJson(this.metadata.getAsJsonObject(name)) : null;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void close() {
        this.resources.close();
    }
}
