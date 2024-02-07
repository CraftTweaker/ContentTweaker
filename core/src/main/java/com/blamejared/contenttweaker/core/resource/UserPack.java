package com.blamejared.contenttweaker.core.resource;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraft.FileUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.server.packs.resources.IoSupplier;
import org.apache.commons.io.input.ReaderInputStream;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Set;

final class UserPack implements PackResources {
    private final Gson gson;
    private final String packId;
    private final PackResources resources;
    private final JsonObject metadata;

    UserPack(final Gson gson, final String packId, final PackResources resources, final JsonObject metadata) {
        this.gson = Objects.requireNonNull(gson);
        this.packId = Objects.requireNonNull(packId);
        this.resources = Objects.requireNonNull(resources);
        this.metadata = Objects.requireNonNull(metadata);
    }

    @Nullable
    @Override
    public IoSupplier<InputStream> getRootResource(final String... strings) {
        FileUtil.validatePath(strings);
        if (strings.length == 1 && PACK_META.equals(strings[0])) {
            return () -> new ReaderInputStream(new StringReader(this.gson.toJson(this.metadata)), StandardCharsets.UTF_8);
        }
        return this.resources.getRootResource(strings);
    }

    @Override
    public IoSupplier<InputStream> getResource(final PackType packType, final ResourceLocation resourceLocation) {
        return this.resources.getResource(packType, resourceLocation);
    }

    @Override
    public void listResources(final PackType packType, final String s, final String s1, final ResourceOutput output) {
        this.resources.listResources(packType, s, s1, output);
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
    public String packId() {
        return this.packId;
    }

    @Override
    public boolean isBuiltin() {
        return this.resources.isBuiltin();
    }

    @Override
    public void close() {
        this.resources.close();
    }
}
