package com.blamejared.contenttweaker.core.resource.trundle;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.AccessMode;
import java.nio.file.ClosedFileSystemException;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryStream;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.nio.file.spi.FileSystemProvider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;

final class TrundleFileSystem extends FileSystem {
    @FunctionalInterface
    private interface ExceptionalRunnable<T> {
        T run() throws IOException;
    }

    private final TrundleFileSystemProvider provider;
    private final String name;

    private volatile TrundleMemoryFileStore fileStore;
    private volatile TrundleDirectory root;
    private volatile boolean closed;

    private TrundleFileSystem(final TrundleFileSystemProvider provider, final String name, final String rootName) {
        this.provider = Objects.requireNonNull(provider);
        this.name = Objects.requireNonNull(name);
        this.fileStore = new TrundleMemoryFileStore("memory@" + name);
        this.root = new TrundleDirectory(Objects.requireNonNull(rootName), new ArrayList<>());
        this.closed = false;
    }

    static TrundleFileSystem of(final TrundleFileSystemProvider provider, final String name, final Map<String, ?> environment) {
        Objects.requireNonNull(provider);
        Objects.requireNonNull(name);
        Objects.requireNonNull(environment);
        final String rootName = Optional.ofNullable(environment.get("rootName")).map(String.class::cast).orElse("");
        return new TrundleFileSystem(provider, name, rootName);
    }

    String name() {
        return this.name;
    }

    @Override
    public FileSystemProvider provider() {
        return this.provider;
    }

    @Override
    public void close() {
        if (this.closed) return;
        synchronized (this) {
            if (this.closed) return;
            // TODO("Close all streams and similar, also implement checks")
            this.root = null; // Allow garbage collection of the entire file system
            this.fileStore = null;
            this.closed = true;
            this.provider.removeFileSystem(this.name);
        }
    }

    @Override
    public boolean isOpen() {
        return !this.closed;
    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Override
    public String getSeparator() {
        return "/";
    }

    @Override
    public Iterable<Path> getRootDirectories() {
        if (this.closed) {
            throw new ClosedFileSystemException();
        }
        return Collections.singleton(TrundlePath.of(this, "/"));
    }

    @Override
    public Iterable<FileStore> getFileStores() {
        if (this.closed) {
            throw new ClosedFileSystemException();
        }
        return Collections.singletonList(this.fileStore);
    }

    @Override
    public Set<String> supportedFileAttributeViews() {
        if (this.closed) {
            throw new ClosedFileSystemException();
        }
        return Collections.singleton("basic");
    }

    @NotNull
    @Override
    public Path getPath(@NotNull final String first, @NotNull final String... more) {
        Objects.requireNonNull(first);
        Objects.requireNonNull(more);
        if (this.closed) {
            throw new ClosedFileSystemException();
        }
        final StringBuilder path = new StringBuilder(first);
        for (final String additional : more) {
            if (additional.isEmpty()) continue;
            path.append(this.getSeparator()).append(additional);
        }
        return TrundlePath.of(this, path.toString());
    }

    @Override
    public PathMatcher getPathMatcher(final String syntaxAndPattern) {
        Objects.requireNonNull(syntaxAndPattern);
        if (this.closed) {
            throw new ClosedFileSystemException();
        }
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public UserPrincipalLookupService getUserPrincipalLookupService() {
        throw new UnsupportedOperationException("A Trundle file system does not have a user principal lookup service");
    }

    @Override
    public WatchService newWatchService() {
        if (this.closed) {
            throw new ClosedFileSystemException();
        }
        throw new UnsupportedOperationException("Not yet implemented");
    }

    SeekableByteChannel seekableByteChannel(final TrundlePath path, final Set<? extends OpenOption> options, final FileAttribute<?>... attributes) throws IOException {
        if (this.closed) {
            throw new ClosedFileSystemException();
        }
        return this.catching(() -> TrundleChannels.seekableByteChannel(this.resolve(path), options, attributes));
    }

    DirectoryStream<Path> directoryStream(final TrundlePath path, final DirectoryStream.Filter<? super Path> filter) throws IOException {
        if (this.closed) {
            throw new ClosedFileSystemException();
        }
        return this.catching(() -> TrundleDirectoryStream.of(this, this.resolve(path), filter));
    }

    void createDirectory(final TrundlePath path, final FileAttribute<?>... attrs) throws IOException {
        if (this.closed) {
            throw new ClosedFileSystemException();
        }
        this.catching(() -> TrundleResources.directory(this.resolve(path), attrs));
    }

    void delete(final TrundlePath path) throws IOException {
        if (this.closed) {
            throw new ClosedFileSystemException();
        }
        this.catching(() -> TrundleResources.delete(this.resolve(path)));
    }

    void copy(final TrundlePath from, final TrundlePath to, final CopyOption... options) throws IOException {
        if (this.closed) {
            throw new ClosedFileSystemException();
        }
        this.catching(() -> TrundleResources.copy(this.resolve(from), this.resolve(to), options));
    }

    void move(final TrundlePath from, final TrundlePath to, final CopyOption... options) throws IOException {
        if (this.closed) {
            throw new ClosedFileSystemException();
        }
        this.catching(() -> TrundleResources.move(this.resolve(from), this.resolve(to), options));
    }

    boolean isSameFile(final TrundlePath a, final TrundlePath b) throws IOException {
        if (this.closed) {
            throw new ClosedFileSystemException();
        }
        return this.catching(() -> TrundleResources.same(this.resolve(a), this.resolve(b)));
    }

    boolean isHidden(final TrundlePath path) throws IOException {
        if (this.closed) {
            throw new ClosedFileSystemException();
        }
        return this.catching(() -> TrundleResources.hidden(this.resolve(path)));
    }

    FileStore fileStore(@SuppressWarnings("unused") final TrundlePath path) {
        if (this.closed) {
            throw new ClosedFileSystemException();
        }
        return this.fileStore;
    }

    void checkAccess(final TrundlePath path, final AccessMode... modes) throws IOException {
        if (this.closed) {
            throw new ClosedFileSystemException();
        }
        this.catching(() -> TrundleResources.access(this.resolveOrNoSuchFile(path), modes));
    }

    <V extends FileAttributeView> V fileAttributeView(final TrundlePath path, final Class<V> type, final LinkOption... options) {
        if (this.closed) {
            throw new ClosedFileSystemException();
        }
        return TrundleAttributes.attributeView(this.resolve(path), type, options);
    }

    <A extends BasicFileAttributes> A attributes(final TrundlePath path, final Class<A> type, final LinkOption... options) throws IOException {
        if (this.closed) {
            throw new ClosedFileSystemException();
        }
        return this.catching(() -> TrundleAttributes.attributes(this.resolve(path), type, options));
    }

    Map<String, Object> attributes(final TrundlePath path, final String attributes, final LinkOption... options) throws IOException {
        if (this.closed) {
            throw new ClosedFileSystemException();
        }
        return this.catching(() -> TrundleAttributes.attributes(this.resolve(path), attributes, options));
    }

    void attribute(final TrundlePath path, final String attribute, final Object value, final LinkOption... options) throws IOException {
        if (this.closed) {
            throw new ClosedFileSystemException();
        }
        this.catching(() -> TrundleAttributes.attribute(this.resolve(path), attribute, value, options));
    }

    InputStream inputStream(final TrundlePath path, final OpenOption... options) throws IOException {
        if (this.closed) {
            throw new ClosedFileSystemException();
        }
        return Channels.newInputStream(path.seekableByteChannel(Set.of(options)));
    }

    OutputStream outputStream(final TrundlePath path, final OpenOption... options) throws IOException {
        if (this.closed) {
            throw new ClosedFileSystemException();
        }
        return Channels.newOutputStream(path.seekableByteChannel(Set.of(options)));
    }

    FileChannel fileChannel(final TrundlePath path, final Set<? extends OpenOption> options, final FileAttribute<?>... attrs) throws IOException {
        if (this.closed) {
            throw new ClosedFileSystemException();
        }
        return this.catching(() -> TrundleChannels.fileChannel(this.resolve(path), options, attrs));
    }

    AsynchronousFileChannel asyncFileChannel(
            final TrundlePath path,
            final Set<? extends OpenOption> options,
            final ExecutorService executor,
            final FileAttribute<?>... attrs
    ) throws IOException {
        if (this.closed) {
            throw new ClosedFileSystemException();
        }
        return this.catching(() -> TrundleChannels.asyncFileChannel(this.resolve(path), options, executor, attrs));
    }

    boolean deleteIfExist(final TrundlePath path) throws IOException {
        if (this.closed) {
            throw new ClosedFileSystemException();
        }
        return this.catching(() -> TrundleResources.deleteIfExists(this.resolve(path)));
    }

    private TrundlePathResolutionResult resolveOrNoSuchFile(final TrundlePath path) throws IOException {
        try {
            return this.resolve(path);
        } catch (final TrundleException e) {
            if (e.code() != TrundleException.Code.RESOLUTION_ERROR) {
                throw e;
            }

            final String[] portions = path.pathPortions();
            final int last = portions.length - 1;
            final String name = portions[last];
            final IOException noFileException = new NoSuchFileException(name);
            noFileException.initCause(e);
            throw noFileException;
        }
    }

    private TrundlePathResolutionResult resolve(final TrundlePath path) {
        try {
            if (path.getParent() == null) { // If this is the root path, resolution is immediate with this.root
                return new TrundlePathResolutionResult(path, null, "/", this.root);
            }

            final String[] portions = path.pathPortions();
            final int last = portions.length - 1;
            final String name = portions[last];

            TrundleResource resource = this.root;
            for (int i = 0; i < last; ++i) {
                resource = resource.resolve(portions[i]);
            }

            return new TrundlePathResolutionResult(path, resource, name, resource.tryResolve(name));
        } catch (final Exception e) {
            throw new TrundleException(TrundleException.Code.RESOLUTION_ERROR, "Unable to resolve path '" + path + "' within the file system", e);
        }
    }

    private <T> T catching(final ExceptionalRunnable<T> runnable) throws IOException {
        try {
            return runnable.run();
        } catch (final TrundleException e) {
            throw e.asIoException();
        } catch (final NullPointerException e) { // NPE can occur when manipulating the root path in unexpected ways: this is never intended
            throw new IOException("A critical error has been encountered while handling the file system", e);
        }
    }
}
