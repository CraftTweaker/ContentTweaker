package com.blamejared.contenttweaker.core.resource;

import com.google.gson.JsonObject;
import net.minecraft.FileUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.server.packs.resources.IoSupplier;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.FileSystem;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

final class RuntimePack {
    private static final class ResourceWalker extends SimpleFileVisitor<Path> {
        private final Path root;
        private final Consumer<Path> consumer;

        ResourceWalker(final Path root, final Consumer<Path> consumer) {
            this.root = Objects.requireNonNull(root);
            Objects.requireNonNull(consumer);
            this.consumer = p -> consumer.accept(p.normalize());
        }

        @Override
        public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
            final FileVisitResult result = super.visitFile(file, attrs);
            if (result != FileVisitResult.CONTINUE) {
                return result;
            }
            this.consumer.accept(this.root.relativize(file));
            return FileVisitResult.CONTINUE;
        }
    }

    private final String name;
    private final String targetNamespace;
    private final PackType type;
    private final JsonObject metadata;
    private final FileSystem fs;

    RuntimePack(final String name, final String targetNamespace, final PackType type, final JsonObject metadata, final Supplier<FileSystem> fs) {
        this.name = Objects.requireNonNull(name);
        this.targetNamespace = Objects.requireNonNull(targetNamespace);
        this.type = Objects.requireNonNull(type);
        this.metadata = validateMeta(Objects.requireNonNull(metadata));
        this.fs = Objects.requireNonNull(Objects.requireNonNull(fs).get());
    }

    private static JsonObject validateMeta(final JsonObject object) {
        final List<String> invalid = object.entrySet().stream()
                .filter(it -> !(it.getValue() instanceof JsonObject))
                .map(Map.Entry::getKey)
                .toList();
        if (invalid.isEmpty()) {
            return object;
        }
        throw new IllegalArgumentException("Invalid metadata sections " + invalid + ": not a JsonObject");
    }

    IoSupplier<InputStream> rootResource(final String... strings) {
        Objects.requireNonNull(strings);
        FileUtil.validatePath(strings);
        return this.resource(strings);
    }

    IoSupplier<InputStream> resource(final PackType packType, final ResourceLocation resourceLocation) {
        Objects.requireNonNull(packType);
        Objects.requireNonNull(resourceLocation);
        if (packType != this.type || !this.targetNamespace.equals(resourceLocation.getNamespace())) {
            return null;
        }
        return this.resource(resourceLocation.getPath());
    }

    void resources(final PackType packType, final String s, final String s1, final PackResources.ResourceOutput output) {
        Objects.requireNonNull(packType);
        Objects.requireNonNull(s);
        Objects.requireNonNull(s1);
        Objects.requireNonNull(output);
        if (packType != this.type || !this.targetNamespace.equals(s)) {
            return;
        }
        final Path directory = this.pathOf(s1);
        try {
            Files.walkFileTree(directory, Set.of(), Integer.MAX_VALUE, new ResourceWalker(directory, p -> output.accept(this.resourceOf(directory.resolve(p)), this.resource(p))));
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    Set<String> namespaces(final PackType packType) {
        return this.type == packType? Set.of(this.targetNamespace) : Collections.emptySet();
    }

    <T> T metadataSection(final MetadataSectionSerializer<T> metadataSectionSerializer) {
        final String name = metadataSectionSerializer.getMetadataSectionName();
        return this.metadata.has(name)? metadataSectionSerializer.fromJson(this.metadata.getAsJsonObject(name)) : null;
    }

    String packId() {
        return this.name;
    }

    private IoSupplier<InputStream> resource(final String... resourcePath) {
        return this.resource(this.pathOf(resourcePath));
    }

    private IoSupplier<InputStream> resource(final Path path) {
        return Files.exists(path)? IoSupplier.create(path) : null;
    }

    private Path pathOf(final String... resourcePath) {
        return FileUtil.resolvePath(this.fs.getPath("/"), List.of(resourcePath));
    }

    private ResourceLocation resourceOf(final Path path) {
        return new ResourceLocation(this.targetNamespace, this.fs.getPath("/").relativize(path).toString());
    }
}
