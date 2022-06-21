package com.blamejared.contenttweaker.core.resource.trundle;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.AccessMode;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryStream;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemAlreadyExistsException;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.ProviderMismatchException;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.spi.FileSystemProvider;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.regex.Pattern;

//trundle:name@[/?][path?] (with / acting as absolute delimiter)
public final class TrundleFileSystemProvider extends FileSystemProvider {
    public static final String SCHEME = "trundle";

    private final Map<String, TrundleFileSystem> fileSystems;

    public TrundleFileSystemProvider() {
        this.fileSystems = new HashMap<>();
    }

    @Override
    public String getScheme() {
        return SCHEME;
    }

    @Override
    public FileSystem newFileSystem(final URI uri, final Map<String, ?> env) {
        this.checkUri(uri);
        final String name = this.findName(uri);

        synchronized (this.fileSystems) {
            if (this.fileSystems.containsKey(name)) {
                throw new FileSystemAlreadyExistsException("Already created Trundle file system with name " + name);
            }
            final TrundleFileSystem fs = TrundleFileSystem.of(this, name, env);
            this.fileSystems.put(name, fs);
            return fs;
        }
    }

    @Override
    public FileSystem getFileSystem(final URI uri) {
        this.checkUri(uri);
        final String name = this.findName(uri);

        synchronized (this.fileSystems) {
            final TrundleFileSystem fs = this.fileSystems.get(name);
            if (fs == null) {
                throw new FileSystemNotFoundException("No such Trundle file system with name " + name);
            }
            return fs;
        }
    }

    @NotNull
    @Override
    public Path getPath(@NotNull final URI uri) {
        this.checkUri(uri);
        final String path = uri.getRawSchemeSpecificPart().split(Pattern.quote("@"), 2)[1];
        if (path.isEmpty()) {
            throw new IllegalArgumentException("URI " + uri + " does not contain path");
        }
        return this.getFileSystem(uri).getPath(path);
    }

    @Override
    public SeekableByteChannel newByteChannel(final Path path, final Set<? extends OpenOption> options, final FileAttribute<?>... attrs) throws IOException {
        Objects.requireNonNull(path);
        Objects.requireNonNull(options);
        Objects.requireNonNull(attrs);
        return this.trundlePath(path).seekableByteChannel(options, attrs);
    }

    @Override
    public DirectoryStream<Path> newDirectoryStream(final Path dir, final DirectoryStream.Filter<? super Path> filter) throws IOException {
        Objects.requireNonNull(dir);
        Objects.requireNonNull(filter);
        return this.trundlePath(dir).directoryStream(filter);
    }

    @Override
    public void createDirectory(final Path dir, final FileAttribute<?>... attrs) throws IOException {
        Objects.requireNonNull(dir);
        Objects.requireNonNull(attrs);
        this.trundlePath(dir).createDirectory(attrs);
    }

    @Override
    public void delete(final Path path) throws IOException {
        Objects.requireNonNull(path);
        this.trundlePath(path).delete();
    }

    @Override
    public void copy(final Path source, final Path target, final CopyOption... options) throws IOException {
        Objects.requireNonNull(source);
        Objects.requireNonNull(target);
        Objects.requireNonNull(options);
        this.trundlePath(source).copy(this.trundlePath(target), options);
    }

    @Override
    public void move(final Path source, final Path target, final CopyOption... options) throws IOException {
        Objects.requireNonNull(source);
        Objects.requireNonNull(target);
        Objects.requireNonNull(options);
        this.trundlePath(source).move(this.trundlePath(target), options);
    }

    @Override
    public boolean isSameFile(final Path path, final Path path2) throws IOException {
        if (path == path2) return true;
        if (!(path2 instanceof TrundlePath other)) return false;
        return this.trundlePath(path).isSameFile(other);
    }

    @Override
    public boolean isHidden(final Path path) throws IOException {
        Objects.requireNonNull(path);
        return this.trundlePath(path).isHidden();
    }

    @Override
    public FileStore getFileStore(final Path path) throws IOException {
        Objects.requireNonNull(path);
        return this.trundlePath(path).fileStore();
    }

    @Override
    public void checkAccess(final Path path, final AccessMode... modes) throws IOException {
        Objects.requireNonNull(path);
        Objects.requireNonNull(modes);
        this.trundlePath(path).checkAccess(modes);
    }

    @Override
    public <V extends FileAttributeView> V getFileAttributeView(final Path path, final Class<V> type, final LinkOption... options) {
        Objects.requireNonNull(path);
        Objects.requireNonNull(type);
        Objects.requireNonNull(options);
        return this.trundlePath(path).fileAttributeView(type, options);
    }

    @Override
    public <A extends BasicFileAttributes> A readAttributes(final Path path, final Class<A> type, final LinkOption... options) throws IOException {
        Objects.requireNonNull(path);
        Objects.requireNonNull(type);
        Objects.requireNonNull(options);
        return this.trundlePath(path).attributes(type, options);
    }

    @Override
    public Map<String, Object> readAttributes(final Path path, final String attributes, final LinkOption... options) throws IOException {
        Objects.requireNonNull(path);
        Objects.requireNonNull(attributes);
        Objects.requireNonNull(options);
        return this.trundlePath(path).attributes(attributes, options);
    }

    @Override
    public void setAttribute(final Path path, final String attribute, final Object value, final LinkOption... options) throws IOException {
        Objects.requireNonNull(path);
        Objects.requireNonNull(attribute);
        Objects.requireNonNull(value);
        Objects.requireNonNull(options);
        this.trundlePath(path).attribute(attribute, value, options);
    }

    @Override
    public FileSystem newFileSystem(final Path path, final Map<String, ?> env) {
        Objects.requireNonNull(path);
        Objects.requireNonNull(env);
        throw new UnsupportedOperationException("Unable to access '" + path + "' as a Trundle file system");
    }

    @Override
    public InputStream newInputStream(final Path path, final OpenOption... options) throws IOException {
        Objects.requireNonNull(path);
        Objects.requireNonNull(options);
        return this.trundlePath(path).inputStream(options);
    }

    @Override
    public OutputStream newOutputStream(final Path path, final OpenOption... options) throws IOException {
        Objects.requireNonNull(path);
        Objects.requireNonNull(options);
        return this.trundlePath(path).outputStream(options);
    }

    @Override
    public FileChannel newFileChannel(final Path path, final Set<? extends OpenOption> options, final FileAttribute<?>... attrs) throws IOException {
        Objects.requireNonNull(path);
        Objects.requireNonNull(options);
        Objects.requireNonNull(attrs);
        return this.trundlePath(path).fileChannel(options, attrs);
    }

    @Override
    public AsynchronousFileChannel newAsynchronousFileChannel(
            final Path path,
            final Set<? extends OpenOption> options,
            final ExecutorService executor,
            final FileAttribute<?>... attrs
    ) throws IOException {
        Objects.requireNonNull(path);
        Objects.requireNonNull(options);
        Objects.requireNonNull(executor);
        Objects.requireNonNull(attrs);
        return this.trundlePath(path).asyncFileChannel(options, executor, attrs);
    }

    @Override
    public void createSymbolicLink(final Path link, final Path target, final FileAttribute<?>... attrs) {
        Objects.requireNonNull(link);
        Objects.requireNonNull(target);
        Objects.requireNonNull(attrs);
        throw new UnsupportedOperationException("Symbolic link creation is not supported in a Trundle file system");
    }

    @Override
    public void createLink(final Path link, final Path existing) {
        Objects.requireNonNull(link);
        Objects.requireNonNull(existing);
        throw new UnsupportedOperationException("Link creation is not supported in a Trundle file system");
    }

    @Override
    public boolean deleteIfExists(final Path path) throws IOException {
        Objects.requireNonNull(path);
        return this.trundlePath(path).deleteIfExists();
    }

    @Override
    public Path readSymbolicLink(final Path link) {
        Objects.requireNonNull(link);
        throw new UnsupportedOperationException("Symbolic link reading is not supported in a Trundle file system");
    }

    @SuppressWarnings("resource") // Called when the file system is being closed
    void removeFileSystem(final String name) {
        synchronized (this.fileSystems) {
            this.fileSystems.remove(name);
        }
    }

    private void checkUri(final URI uri) {
        Objects.requireNonNull(uri);
        if (!uri.getScheme().equals(this.getScheme())) {
            throw new IllegalArgumentException("Unable to create a file system with a non-" + this.getScheme() + " URI");
        }
        final String raw = uri.getRawSchemeSpecificPart();
        if (!raw.contains("@")) {
            throw new IllegalArgumentException("Invalid URL for " + this.getScheme() + " URI: missing type marker");
        }
    }

    private String findName(final URI uri) {
        final String raw = uri.getRawSchemeSpecificPart();
        final String[] splits = raw.split(Pattern.quote("@"), 2);
        return splits[0];
    }

    private TrundlePath trundlePath(final Path path) {
        if (!(path instanceof TrundlePath trundlePath)) {
            throw new ProviderMismatchException("Mismatch with path " + path);
        }
        return trundlePath;
    }
}
