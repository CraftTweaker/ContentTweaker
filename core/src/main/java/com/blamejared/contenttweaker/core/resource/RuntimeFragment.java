package com.blamejared.contenttweaker.core.resource;

import com.blamejared.contenttweaker.core.api.resource.ResourceFragment;
import com.blamejared.contenttweaker.core.api.resource.ResourceSerializer;
import com.blamejared.contenttweaker.core.resource.trundle.TrundleFileSystemProvider;
import com.blamejared.contenttweaker.core.service.ServiceManager;
import com.blamejared.crafttweaker.api.util.GenericUtil;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

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

        void finalize(final Map<String, LazyResource<?>> lazies, final AtomicBoolean finalizedOut, final BiConsumer<String, byte[]> provider) {
            if (finalizedOut.get()) {
                throw new IllegalStateException();
            }
            lazies.forEach((path, res) -> provider.accept(path, res.provide(ResourceSerializer::serialize)));
            lazies.clear();
            finalizedOut.set(true);
        }

        private FileSystem create() {
            try {
                // TODO("Test if Forge/Fabric/Java ever fixes the issue of FileSystems being discovered at a weird time")
                return FileSystems.newFileSystem(new URI("%s:%s@".formatted(TrundleFileSystemProvider.SCHEME, this.fsId)), Collections.emptyMap(), this.getClass().getClassLoader());
            } catch (final URISyntaxException | IOException | FileSystemAlreadyExistsException | ProviderNotFoundException e) {
                throw new RuntimeException("Unable to create file system " + this.fsId, e);
            }
        }
    }

    private static final class LazyResource<T> {
        private final ResourceSerializer<T> serializer;
        private T current;

        private LazyResource(final ResourceSerializer<T> serializer, final T current) {
            this.serializer = serializer;
            this.current = current;
        }

        ResourceSerializer<T> serializer() {
            return this.serializer;
        }

        void update(final Function<T, T> function) {
            this.current = function.apply(this.current);
        }

        T resource() {
            return this.current;
        }

        byte[] provide(final BiFunction<ResourceSerializer<T>, T, byte[]> provider) {
            return provider.apply(this.serializer(), this.resource());
        }
    }

    @FunctionalInterface
    private interface ExceptionalConsumer<T, E extends Exception> {
        void accept(final T t) throws E;
    }

    private final ResourceFragment.Key key;
    private final FileSystemOnDemand fs;
    private final Map<String, LazyResource<?>> lazyResources;
    private final AtomicBoolean finalized;

    private RuntimeFragment(final ResourceFragment.Key key, final FileSystemOnDemand fs) {
        this.key = key;
        this.fs = fs;
        this.lazyResources = new HashMap<>();
        this.finalized = new AtomicBoolean(false);
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
    public void provideTemplated(final String path, final String template) {
        Objects.requireNonNull(path);
        Objects.requireNonNull(template);
        final Path base = ServiceManager.platform().locateResource("meta", "template");
        final Path target = base.resolve(template);
        this.provideTemplated(path, target);
    }

    @Override
    public void provideTemplated(final String path, final Path template) {
        Objects.requireNonNull(path);
        Objects.requireNonNull(template);
        this.provide(path, channel -> this.provide(channel, template));
    }

    @Override
    public <T> void provideFixed(final String path, final T resource, final ResourceSerializer<T> serializer) {
        if (this.finalized.get()) {
            throw new IllegalStateException("Unable to provide more resources when the file system has been finalized");
        }
        this.provide(Objects.requireNonNull(path), Objects.requireNonNull(Objects.requireNonNull(serializer).serialize(Objects.requireNonNull(resource))));
    }

    @Override
    public <T> void provideOrAlter(final String path, final Supplier<T> creator, final Function<T, T> alterationFunction, final ResourceSerializer<T> serializer) {
        if (this.finalized.get()) {
            throw new IllegalStateException("Unable to provide more resources when the file system has been finalized");
        }
        Objects.requireNonNull(path);
        Objects.requireNonNull(creator);
        Objects.requireNonNull(alterationFunction);
        Objects.requireNonNull(serializer);
        final LazyResource<?> resource = this.lazyResources.computeIfAbsent(path, it -> new LazyResource<>(serializer, creator.get()));
        if (resource.serializer() != serializer) {
            throw new IllegalStateException("Given serializer " + serializer + " does not match previous " + resource.serializer());
        }
        final LazyResource<T> typedResource = GenericUtil.uncheck(resource);
        typedResource.update(alterationFunction);
    }

    @Override
    public void close() throws Exception {
        this.fs.close();
    }

    ResourceFragment.Key key() {
        return this.key;
    }

    FileSystem fs() {
        if (!this.finalized.get()) {
            this.fs.finalize(this.lazyResources, this.finalized, this::provide);
        }
        return this.fs0();
    }

    String fsId() {
        return this.fs.id();
    }

    private FileSystem fs0() {
        return this.fs.get();
    }

    private void provide(final String path, final byte... resource) {
        this.provide(path, channel -> channel.write(ByteBuffer.wrap(Arrays.copyOf(resource, resource.length))));
    }

    private void provide(final String path, final ExceptionalConsumer<SeekableByteChannel, IOException> consumer) {
        try {
            final Path target = this.fs0().getPath(path).toAbsolutePath().normalize();
            final Path parent = target.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
            try (final SeekableByteChannel channel = Files.newByteChannel(target, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE)) {
                consumer.accept(channel);
            }
        } catch (final IOException e) {
            throw new UncheckedIOException("Unable to provide resource for path " + path + " in fragment " + this.key(), e);
        }
    }

    private void provide(final SeekableByteChannel outChannel, final Path template) throws IOException {
        try (final SeekableByteChannel inChannel = Files.newByteChannel(template, StandardOpenOption.READ)) {
            int read = 0;
            final int estimatedLength = (int) Math.min(inChannel.size(), Integer.MAX_VALUE);
            final ByteBuffer buffer = ByteBuffer.allocate(estimatedLength).order(ByteOrder.nativeOrder());
            while (read != -1) {
                if ((read = inChannel.read(buffer)) > 0) {
                    buffer.flip();
                    outChannel.write(buffer);
                }
                buffer.clear();
            }
        }
    }
}
