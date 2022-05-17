package com.blamejared.contenttweaker.core.resource;

import com.blamejared.contenttweaker.core.api.resource.ResourceFragment;
import com.blamejared.contenttweaker.core.api.resource.ResourceSerializer;
import com.blamejared.contenttweaker.core.resource.trundle.TrundleFileSystemProvider;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemAlreadyExistsException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.ProviderNotFoundException;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

final class RuntimeFragment implements ResourceFragment, AutoCloseable {
    private static final class FileSystemOnDemand implements AutoCloseable {
        private final String fsId;

        private volatile boolean initialized;
        private volatile FileSystem fs;

        FileSystemOnDemand(final String fsId) {
            this.fsId = Objects.requireNonNull(fsId);
            this.initialized = false;
            this.fs = null;
        }

        @Override
        public void close() throws Exception {
            synchronized (this) {
                if (this.initialized) {
                    this.fs.close();
                }
            }
        }

        FileSystem get() {
            if (!this.initialized) {
                synchronized (this) {
                    if (!this.initialized) {
                        this.fs = this.create();
                        this.initialized = true;
                    }
                }
            }
            return this.fs;
        }

        String id() {
            return this.fsId;
        }

        private FileSystem create() {
            try {
                return FileSystems.newFileSystem(new URI("%s:%s@".formatted(TrundleFileSystemProvider.SCHEME, this.fsId)), Collections.emptyMap());
            } catch (final URISyntaxException | IOException | FileSystemAlreadyExistsException | ProviderNotFoundException e) {
                throw new RuntimeException("Unable to create file system " + this.fsId);
            }
        }
    }

    private final ResourceFragment.Key key;
    private final FileSystemOnDemand fs;

    private RuntimeFragment(final ResourceFragment.Key key, final FileSystemOnDemand fs) {
        this.key = key;
        this.fs = fs;
    }

    static RuntimeFragment of(final ResourceFragment.Key key) {
        final String typeId = switch (key.type()) {
            case CLIENT_RESOURCES -> "ass";
            case SERVER_DATA -> "dat";
        };
        final String fsId = "%s:%s".formatted(key.id(), typeId);
        final FileSystemOnDemand fs = new FileSystemOnDemand(fsId);
        return new RuntimeFragment(key, fs);
    }

    @Override
    public <T> void provide(final String path, final T resource, final ResourceSerializer<T> serializer) {
        this.provide(Objects.requireNonNull(path), Objects.requireNonNull(Objects.requireNonNull(serializer).serialize(Objects.requireNonNull(resource))));
    }

    @Override
    public void close() throws Exception {
        this.fs.close();
    }

    ResourceFragment.Key key() {
        return this.key;
    }

    FileSystem fs() {
        return this.fs.get();
    }

    String fsId() {
        return this.fs.id();
    }

    private void provide(final String path, final byte... resource) {
        try {
            final Path target = this.fs().getPath(path).toAbsolutePath().normalize();
            if (Files.exists(target)) {
                throw new IllegalArgumentException("A resource for path " + path + " has already been provided for fragment " + this.key());
            }
            final ByteBuffer contents = ByteBuffer.wrap(Arrays.copyOf(resource, resource.length)); // Defensive copy just in case
            try (final SeekableByteChannel channel = Files.newByteChannel(target, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE)) {
                channel.position(0L);
                channel.write(contents);
            }
        } catch (final IOException e) {
            throw new UncheckedIOException("Unable to provide resource for path " + path, e);
        }
    }
}
