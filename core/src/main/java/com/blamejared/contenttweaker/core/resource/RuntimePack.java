package com.blamejared.contenttweaker.core.resource;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystem;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

final class RuntimePack {
    private static final class ResourceGatherer extends SimpleFileVisitor<Path> {
        private final Path root;
        private final Consumer<Path> consumer;

        ResourceGatherer(final Path root, final Consumer<Path> consumer) {
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

    InputStream rootResource(final String s) throws IOException {
        Objects.requireNonNull(s);
        if (s.contains("/") || s.contains("\\")) {
            throw new IllegalStateException("Root resources can only be file names");
        }
        return this.resource(s);
    }

    InputStream resource(final PackType packType, final ResourceLocation resourceLocation) throws IOException {
        Objects.requireNonNull(packType);
        Objects.requireNonNull(resourceLocation);
        if (packType != this.type || !this.targetNamespace.equals(resourceLocation.getNamespace())) {
            return null;
        }
        return this.resource(resourceLocation.getPath());
    }

    Collection<ResourceLocation> resources(final PackType packType, final String s, final String s1, final Predicate<ResourceLocation> predicate) {
        Objects.requireNonNull(packType);
        Objects.requireNonNull(s);
        Objects.requireNonNull(s1);
        Objects.requireNonNull(predicate);
        if (packType != this.type || !this.targetNamespace.equals(s)) {
            return Collections.emptySet();
        }
        final Collection<ResourceLocation> resources = new ArrayList<>();
        final Path directory = this.pathOf(s1);
        try {
            Files.walkFileTree(directory, Set.of(), Integer.MAX_VALUE, new ResourceGatherer(directory, p -> resources.add(this.resourceOf(directory.resolve(p)))));
        } catch (final IOException e) {
            return Collections.emptySet();
        }
        return resources;
    }

    boolean knowsResource(final PackType packType, final ResourceLocation resourceLocation) {
        Objects.requireNonNull(packType);
        Objects.requireNonNull(resourceLocation);
        return packType == this.type && this.targetNamespace.equals(resourceLocation.getNamespace()) && Files.exists(this.pathOf(resourceLocation.getPath()));
    }

    Set<String> namespaces(final PackType packType) {
        return this.type == packType? Set.of(this.targetNamespace) : Collections.emptySet();
    }

    <T> T metadataSection(final MetadataSectionSerializer<T> metadataSectionSerializer) {
        final String name = metadataSectionSerializer.getMetadataSectionName();
        return this.metadata.has(name)? metadataSectionSerializer.fromJson(this.metadata.getAsJsonObject(name)) : null;
    }

    String name() {
        return this.name;
    }

    private InputStream resource(final String resource) throws IOException {
        final Path path = this.pathOf(resource);
        return Files.exists(path)? Files.newInputStream(path, StandardOpenOption.READ) : null;
    }

    private Path pathOf(final String resource) {
        return this.fs.getPath(resource).toAbsolutePath();
    }

    private ResourceLocation resourceOf(final Path path) {
        return new ResourceLocation(this.targetNamespace, this.fs.getPath("/").relativize(path).toString());
    }
}
