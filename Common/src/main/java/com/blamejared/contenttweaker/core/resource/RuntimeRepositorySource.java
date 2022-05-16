package com.blamejared.contenttweaker.core.resource;

import com.blamejared.contenttweaker.core.ContentTweakerCore;
import com.blamejared.contenttweaker.core.api.ContentTweakerConstants;
import com.blamejared.contenttweaker.core.resource.trundle.TrundleFileSystemProvider;
import com.google.gson.JsonObject;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.RepositorySource;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public final class RuntimeRepositorySource implements RepositorySource {
    private static final int CLIENT_FORMAT = 8;
    private static final int SERVER_FORMAT = 8;

    private final PackType type;

    private RuntimeRepositorySource(final PackType type) {
        this.type = type;
    }

    public static RuntimeRepositorySource of(final PackType type) {
        Objects.requireNonNull(type);
        return new RuntimeRepositorySource(type);
    }

    @Override
    public void loadPacks(final Consumer<Pack> consumer, final Pack.PackConstructor packConstructor) {
        final RuntimeResourceManager manager = ContentTweakerCore.core().resourceManager();
        final Map<String, Map<String, byte[]>> resources = manager.allResources(this.type);
        resources.forEach((namespace, resourceData) -> this.pack(namespace, resourceData.entrySet(), consumer, packConstructor));
    }

    private void pack(final String namespace, final Collection<Map.Entry<String, byte[]>> resources, final Consumer<Pack> consumer, final Pack.PackConstructor packConstructor) {
        final Pack pack = this.createPack(this.type, namespace, resources, packConstructor);
        consumer.accept(pack);
    }

    private Pack createPack(final PackType type, final String id, final Collection<Map.Entry<String, byte[]>> resources, final Pack.PackConstructor constructor) {
        final String typeId = switch (type) {
            case CLIENT_RESOURCES -> "ass";
            case SERVER_DATA -> "dat";
        };
        final String fsId = "%s:%s".formatted(id, typeId);
        final String packId = ContentTweakerConstants.rl("runtime/" + fsId.replace(':', '/')).toString();
        final Pack pack = Pack.create(id, true, () -> this.createPack(type, id, packId, resources), constructor, Pack.Position.TOP, this::decorateSource);
        if (pack == null) {
            throw new IllegalStateException("An error occurred while generating runtime " + ContentTweakerConstants.MOD_NAME + " pack '" + packId + "'");
        }
        return pack;
    }

    private PackResources createPack(final PackType type, final String target, final String packId, final Collection<Map.Entry<String, byte[]>> resources) {
        try {
            final URI uri = new URI("%s:%s@".formatted(TrundleFileSystemProvider.SCHEME, packId));
            final JsonObject metadata = this.makeMetadata(type, packId, target);
            return new RuntimePack(packId, target, type, metadata, () -> {
                try {
                    final FileSystem fs = FileSystems.newFileSystem(uri, Map.of());
                    this.populate(fs, resources);
                    return fs;
                } catch (final IOException e) {
                    throw new UncheckedIOException(e);
                }
            });
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private JsonObject makeMetadata(final PackType type, final String packId, final String target) {
        final JsonObject meta = new JsonObject();

        final JsonObject pack = new JsonObject();
        pack.addProperty("description", "Runtime pack for " + target + " of type " + type.name());
        pack.addProperty("pack_format", this.makeFormat(type));
        meta.add("pack", pack);

        final JsonObject cotData = new JsonObject();
        cotData.addProperty("type", type.toString());
        cotData.addProperty("id", packId);
        cotData.addProperty("target", target);
        meta.add(ContentTweakerConstants.rl("data").toString(), cotData);

        return meta;
    }

    private int makeFormat(final PackType type) {
        return switch (type) {
            case CLIENT_RESOURCES -> CLIENT_FORMAT;
            case SERVER_DATA -> SERVER_FORMAT;
        };
    }

    private Component decorateSource(final Component originalName) {
        return new TranslatableComponent(
                "pack.nameAndSource",
                originalName,
                new TranslatableComponent(ContentTweakerConstants.ln("pack_source.runtime"))
        );
    }

    private void populate(final FileSystem fs, final Collection<Map.Entry<String, byte[]>> resources) {
        resources.forEach(resource -> {
            try {
                final Path path = fs.getPath(resource.getKey()).toAbsolutePath();
                final ByteBuffer contents = ByteBuffer.wrap(resource.getValue());
                try (final SeekableByteChannel channel = Files.newByteChannel(path, StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW)) {
                    channel.position(0L);
                    channel.write(contents);
                }
            } catch (final IOException e) {
                throw new UncheckedIOException("Unable to write resource " + resource.getKey(), e);
            }
        });
    }
}
