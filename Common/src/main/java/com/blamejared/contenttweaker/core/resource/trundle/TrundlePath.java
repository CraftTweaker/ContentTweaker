package com.blamejared.contenttweaker.core.resource.trundle;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.AccessMode;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryStream;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.ProviderMismatchException;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileAttributeView;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;

final class TrundlePath implements Path {
    private record ParsedPath(TrundlePathType type, String root, String path) {}

    private static final String ABS_ROOT = "/";
    private static final String REL_ROOT = "";

    private final TrundleFileSystem fs;
    private final TrundlePathType pathType;
    private final String root;
    private final String path;

    private volatile int[] componentIndexes;
    private int hash;

    private TrundlePath(final TrundleFileSystem fs, final TrundlePathType pathType, final String root, final String path) {
        this.fs = Objects.requireNonNull(fs);
        this.pathType = Objects.requireNonNull(pathType);
        this.root = Objects.requireNonNull(root);
        this.path = elideIfNeeded(root, Objects.requireNonNull(path));
        this.componentIndexes = null;
        this.hash = 0;
    }

    static TrundlePath of(final TrundleFileSystem fs, final String path) {
        Objects.requireNonNull(fs);
        Objects.requireNonNull(path);
        final ParsedPath parsedPath = parse(path);
        return new TrundlePath(fs, parsedPath.type(), parsedPath.root(), parsedPath.path());
    }

    private static ParsedPath parse(final String path) {
        final TrundlePathType type = path.length() > 0 && path.charAt(0) == '/'? TrundlePathType.ABSOLUTE : TrundlePathType.RELATIVE;
        final String root = parseRoot(type, path);
        final String rawPath = path.length() > 0 && type.isAbsolute()? path.substring(1) : path;
        return new ParsedPath(type, root, rawPath);
    }

    private static String parseRoot(final TrundlePathType type, @SuppressWarnings("unused") final String path) {
        return type.isAbsolute()? ABS_ROOT : REL_ROOT; // TODO("Make this better")
    }

    // FIXME("Fix normalization of paths like '../../'")
    private static String normalize(final String original) {
        final StringBuilder builder = new StringBuilder();

        boolean foundSlash = true; // Whether a '/' character has been found: an implicit / is at the beginning
        boolean possiblyParent = false; // Whether '/.' has been identified
        boolean definitelyParent = false; // Whether '/..' has been identified
        for (int i = 0, s = original.length(); i < s; ++i) {
            final char c = original.charAt(i);
            if (c == '/') {
                if (foundSlash) { // Another '/' has been identified previously; situations: '//' (invalid), '/./' (cd), '/../' (parent)
                    if (definitelyParent) { // '/../': the previous component should be removed as it is useless
                        final int previousSlash = builder.lastIndexOf("/");
                        builder.setLength(previousSlash); // Deletes from the last slash (including it)
                        possiblyParent = false;
                        definitelyParent = false;
                        continue;
                    }
                    if (possiblyParent) { // '/./': the previous component should remain, and this part get removed
                        possiblyParent = false;
                        continue;
                    }
                    throw new InvalidPathException(original, "Invalid character in path", i);
                }
                foundSlash = true;
                continue;
            }
            if (c == '.') {
                if (foundSlash) {
                    if (definitelyParent) { // We already have '/..', and this leads to '/...': that's invalid
                        throw new InvalidPathException(original, "Invalid character in parent marker", i);
                    }
                    if (possiblyParent) { // This means we already have '/.', so we are in a '/..' situation
                        definitelyParent = true;
                        continue;
                    }
                    // We are in a '/.' situation: so this might be a parent
                    possiblyParent = true;
                    continue;
                }
                // This period does not follow a slash sequence, so we append it to the builder
                builder.append(c);
                continue;
            }
            if (foundSlash) { // We have a slash sequence followed by a character that is not a slash
                builder.append('/');
                foundSlash = false;
            }
            builder.append(c);
        }

        return builder.substring(1);
    }

    private static String elideIfNeeded(final String root, final String path) {
        return root.equals(path)? path : elide(path); // if root == path, then we are either in empty or root, which are special cased
    }

    private static String elide(final String path) {
        final int last;
        return !path.isEmpty() && path.charAt(last = path.length() - 1) == '/'? path.substring(0, last) : path; // TODO("Verify if this makes sense")
    }

    @NotNull
    @Override
    public FileSystem getFileSystem() {
        return this.fs;
    }

    @Override
    public boolean isAbsolute() {
        return this.pathType.isAbsolute();
    }

    @Override
    public Path getRoot() {
        if (this.root.isEmpty()) {
            return null;
        }
        return new TrundlePath(this.fs, this.pathType, this.root, this.root);
    }

    @Override
    public Path getFileName() {
        if (this.isEmptyPath()) { // In an empty path, the file name is the path itself
            return this;
        }
        if (this.isRoot()) { // A root component references no files
            return null;
        }
        final int[] components = this.components();
        final int lastComponentBegin = components[components.length - 2] + 1;
        final String pathPortion = this.path.substring(lastComponentBegin);
        return new TrundlePath(this.fs, TrundlePathType.RELATIVE, REL_ROOT, pathPortion);
    }

    @Override
    public Path getParent() {
        if (this.isRoot()) { // Root components are on the root, thus no parent
            return null;
        }
        final int[] components = this.components();
        final int parentEnd = components[components.length - 2];
        final String pathPortion = parentEnd == -1? this.root : this.path.substring(0, parentEnd); // If this is child of root, then there's a single component: parent is root or empty
        return new TrundlePath(this.fs, this.pathType, this.root, pathPortion);
    }

    @Override
    public int getNameCount() {
        if (this.isRoot()) {
            return 0;
        }
        return this.components().length - 1;
    }

    @NotNull
    @Override
    public Path getName(final int index) {
        if (this.isRoot()) {
            throw new IllegalArgumentException(Integer.toString(index));
        }
        final String portion = this.extract(index);
        return new TrundlePath(this.fs, TrundlePathType.RELATIVE, REL_ROOT, portion);
    }

    @NotNull
    @Override
    public Path subpath(final int beginIndex, final int endIndex) {
        if (this.isRoot()) {
            throw new IllegalArgumentException();
        }
        final int[] components = this.components();
        if (beginIndex < 0 || beginIndex >= (components.length - 1)) {
            throw new IllegalArgumentException(Integer.toString(beginIndex));
        }
        if (endIndex < 0 || endIndex > (components.length - 1)) {
            throw new IllegalArgumentException(Integer.toString(endIndex));
        }
        final String portion = this.path.substring(components[beginIndex] + 1, components[endIndex]);
        return new TrundlePath(this.fs, TrundlePathType.RELATIVE, REL_ROOT, portion);
    }

    @Override
    public boolean startsWith(@NotNull final Path other) {
        Objects.requireNonNull(other);
        if (!(other instanceof TrundlePath path)) { // Different File System
            return false;
        }
        if (!this.root.equals(path.root)) { // The two root components do not match
            return false;
        }
        if (this.isEmptyPath()) { // If this path is empty, it can only start with an empty path
            return path.isEmptyPath();
        }
        if (this.isRoot()) { // If this path is a root component, it can only start with a root component
            return path.isRoot();
        }
        final int pathCount = path.getNameCount();
        if (this.getNameCount() < pathCount) { // Shorter paths cannot start with longer ones
            return false;
        }

        for (int i = 0; i < pathCount; ++i) {
            final String ourComponent = this.extract(i);
            final String otherComponent = path.extract(i);
            if (!ourComponent.equals(otherComponent)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean endsWith(@NotNull final Path other) {
        Objects.requireNonNull(other);
        if (!(other instanceof TrundlePath path)) { // Different File System
            return false;
        }
        if (this.root.isEmpty() && !path.root.isEmpty()) { // A relative path cannot end with an absolute one
            return false;
        }
        if (!this.root.isEmpty() && !path.root.isEmpty() && !this.root.equals(path.root)) { // If both paths have a root component, but they do not match (how??)
            return false;
        }
        if (this.isEmptyPath()) { // If this path is empty, it can only end with an empty path
            return path.isEmptyPath();
        }
        if (this.isRoot()) { // If this path is the root component, then it can end only with a root component
            return path.isRoot();
        }
        final int ourCount = this.getNameCount();
        final int pathCount = path.getNameCount();
        if (ourCount < pathCount) { // Shorter paths cannot end with longer ones
            return false;
        }

        for (int i = 0; i < pathCount; ++i) {
            final String ourComponent = this.extract(ourCount - 1 - i);
            final String otherComponent = this.extract(pathCount - 1 - i);
            if (!ourComponent.equals(otherComponent)) {
                return false;
            }
        }

        return true;
    }

    @NotNull
    @Override
    public Path normalize() {
        if (this.isRoot() || this.isEmptyPath()) {
            return this; // They are normalized already
        }
        return new TrundlePath(this.fs, this.pathType, this.root, normalize(this.path));
    }

    @NotNull
    @Override
    public Path resolve(@NotNull final Path other) {
        Objects.requireNonNull(other);
        if (!(other instanceof TrundlePath path)) {
            throw new ProviderMismatchException();
        }
        if (path.isAbsolute()) {
            return path;
        }
        if (path.isEmptyPath()) {
            return this;
        }
        final String resolvedPath = this.isRoot()? path.path : this.path + '/' + path.path;
        return new TrundlePath(this.fs, this.pathType, this.root, resolvedPath);
    }

    @NotNull
    @Override
    public Path relativize(@NotNull final Path other) {
        Objects.requireNonNull(other);
        if (!(other instanceof TrundlePath path)) {
            throw new ProviderMismatchException();
        }
        if (this.equals(other)) {
            return new TrundlePath(this.fs, TrundlePathType.RELATIVE, REL_ROOT, "");
        }
        final boolean thisAbsolute = this.isAbsolute();
        if (thisAbsolute != path.isAbsolute()) { // Only one of the two paths has a root component: cannot relativize
            throw new IllegalArgumentException("Cannot relativize with mismatching path types");
        }
        if (this.isEmptyPath()) {
            return other;
        }

        // Relativizing means we need to check where two paths diverge
        int divergenceIndex = -1;
        final int ourComponents = this.getNameCount();
        final int theirComponents = path.getNameCount();
        final int min = Math.min(ourComponents, theirComponents);
        for (int i = 0; i < min; ++i) {
            final String ourComponent = this.extract(i);
            final String theirComponent = path.extract(i);
            if (!ourComponent.equals(theirComponent)) {
                divergenceIndex = i;
                break;
            }
        }

        if (divergenceIndex == -1) { // The two paths never diverged, so one is a parent of the other
            if (theirComponents < ourComponents) { // They are a parent
                final String upwardsWalker = elide("../".repeat(theirComponents - ourComponents));
                return new TrundlePath(this.fs, TrundlePathType.RELATIVE, REL_ROOT, upwardsWalker);
            }
            // We are a parent
            final TrundlePath sub = (TrundlePath) path.subpath(ourComponents, theirComponents);
            return new TrundlePath(this.fs, TrundlePathType.RELATIVE, REL_ROOT, sub.path);
        }

        final String upwardsWalker = "../".repeat(ourComponents - divergenceIndex);
        final String sub = ((TrundlePath) path.subpath(divergenceIndex, theirComponents)).path;
        return new TrundlePath(this.fs, TrundlePathType.RELATIVE, REL_ROOT, upwardsWalker + sub);
    }

    @NotNull
    @Override
    public URI toUri() {
        try {
            return new URI("%s:%s@%s%s".formatted(TrundleFileSystemProvider.SCHEME, this.fs.name(), this.root, this.path));
        } catch (final URISyntaxException e) {
            throw new IllegalStateException(e);
        }
    }

    @NotNull
    @Override
    public Path toAbsolutePath() {
        if (this.isAbsolute()) {
            return this;
        }
        // TODO("Maybe something else?")
        return new TrundlePath(this.fs, TrundlePathType.ABSOLUTE, ABS_ROOT, this.path);
    }

    @NotNull
    @Override
    public Path toRealPath(@NotNull final LinkOption... options) {
        Objects.requireNonNull(options);
        return this.toAbsolutePath(); // We are case-sensitive, so this should match
    }

    @NotNull
    @Override
    public WatchKey register(@NotNull final WatchService watcher, @NotNull final WatchEvent.Kind<?>[] events, final WatchEvent.Modifier... modifiers) {
        Objects.requireNonNull(watcher);
        Objects.requireNonNull(events);
        Objects.requireNonNull(modifiers);
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @NotNull
    @Override
    public File toFile() {
        throw new UnsupportedOperationException("Unable to obtain a file from Trundle path");
    }

    @Override
    public int hashCode() {
        return this.hash == 0? this.hash = Objects.hash(this.pathType, this.root, this.path) : this.hash;
    }

    @Override
    public boolean equals(final Object obj) {
        return this == obj || (obj instanceof TrundlePath other && this.compareTo(other) == 0);
    }

    @Override
    public String toString() {
        return this.isAbsolute()? ABS_ROOT + (this.isRoot()? "" : this.path) : this.path;
    }

    @Override
    public int compareTo(@NotNull final Path other) {
        return this.compareTo((TrundlePath) Objects.requireNonNull(other));
    }

    SeekableByteChannel seekableByteChannel(final Set<? extends OpenOption> options, final FileAttribute<?>... attributes) throws IOException {
        return this.fs.seekableByteChannel(this.resolve(), options, attributes);
    }

    DirectoryStream<Path> directoryStream(final DirectoryStream.Filter<? super Path> filter) throws IOException {
        return this.fs.directoryStream(this.resolve(), filter);
    }

    void createDirectory(final FileAttribute<?>... attrs) throws IOException {
        this.fs.createDirectory(this.resolve(), attrs);
    }

    void delete() throws IOException {
        this.fs.delete(this.resolve());
    }

    void copy(final TrundlePath to, final CopyOption... options) throws IOException {
        this.fs.copy(this.resolve(), to.resolve(), options);
    }

    void move(final TrundlePath to, final CopyOption... options) throws IOException {
        this.fs.move(this.resolve(), to.resolve(), options);
    }

    boolean isSameFile(final TrundlePath other) throws IOException {
        return this.fs.isSameFile(this.resolve(), other.resolve());
    }

    boolean isHidden() throws IOException {
        return this.fs.isHidden(this.resolve());
    }

    FileStore fileStore() throws IOException {
        return this.fs.fileStore(this.resolve());
    }

    void checkAccess(final AccessMode... modes) throws IOException {
        this.fs.checkAccess(this.resolve(), modes);
    }

    <V extends FileAttributeView> V fileAttributeView(final Class<V> type, final LinkOption... options) {
        return this.fs.fileAttributeView(this.resolve(), type, options);
    }

    <A extends BasicFileAttributes> A attributes(final Class<A> type, final LinkOption... options) throws IOException {
        return this.fs.attributes(this.resolve(), type, options);
    }

    Map<String, Object> attributes(final String attributes, final LinkOption... options) throws IOException {
        return this.fs.attributes(this.resolve(), attributes, options);
    }

    void attribute(final String attribute, final Object value, final LinkOption... options) throws IOException {
        this.fs.attribute(this.resolve(), attribute, value, options);
    }

    InputStream inputStream(final OpenOption... options) throws IOException {
        return this.fs.inputStream(this.resolve(), options);
    }

    OutputStream outputStream(final OpenOption... options) throws IOException {
        return this.fs.outputStream(this.resolve(), options);
    }

    FileChannel fileChannel(final Set<? extends OpenOption> options, final FileAttribute<?>... attrs) throws IOException {
        return this.fs.fileChannel(this.resolve(), options, attrs);
    }

    AsynchronousFileChannel asyncFileChannel(final Set<? extends OpenOption> options, final ExecutorService executor, final FileAttribute<?>... attrs) throws IOException {
        return this.fs.asyncFileChannel(this.resolve(), options, executor, attrs);
    }

    boolean deleteIfExists() throws IOException {
        return this.fs.deleteIfExist(this.resolve());
    }

    String[] pathPortions() {
        final int[] components = this.components();
        final String[] portions = new String[components.length - 1];
        for (int i = 0, s = portions.length; i < s; ++i) {
            portions[i] = elide(this.extract(i));
        }
        return portions;
    }

    private int[] components() {
        if (this.isRoot()) {
            throw new IllegalStateException("Cannot compute components for root");
        }

        if (this.componentIndexes != null) {
            return this.componentIndexes;
        }

        synchronized (this) {
            if (this.componentIndexes != null) {
                return this.componentIndexes;
            }

            final String path = this.path;
            int components = this.isEmptyPath()? 0 : 1; // By default, every non-empty path has at least one component
            for (final char c : path.toCharArray()) {
                if (c == '/') {
                    ++components;
                }
            }

            final int[] array = new int[components + 1];
            final int length = path.length();
            array[0] = -1;
            array[components] = length;

            int index = 1;
            for (int i = 0; i < length; ++i) {
                if (path.charAt(i) == '/') {
                    array[index++] = i;
                }
            }

            this.componentIndexes = array;
        }

        return this.componentIndexes;
    }

    private int compareTo(final TrundlePath other) {
        return this.path.compareTo(other.path); // TODO("Maybe rework?")
    }

    private boolean isEmptyPath() { // An empty path is a relative path that points to nothing, essentially truffle:name@
        return this.root.isEmpty() && this.path.isEmpty();
    }

    private boolean isRoot() { // A root path is an absolute path whose path component matches the root, essentially truffle:name@/
        // NOTE: It is in general impossible for path to equal root in absolute paths, because the leading slash is always removed
        return !this.root.isEmpty() && this.path.equals(this.root);
    }

    private String extract(final int index) {
        if (this.isRoot()) {
            throw new IllegalStateException("Cannot extract component of root");
        }
        final int[] components;
        if (index < 0 || index >= ((components = this.components()).length - 1)) {
            throw new IllegalArgumentException(Integer.toString(index));
        }
        return this.path.substring(components[index] + 1, components[index + 1]);
    }

    private TrundlePath resolve() {
        return (TrundlePath) this.toAbsolutePath(); // TODO("Maybe convert")
    }
}
