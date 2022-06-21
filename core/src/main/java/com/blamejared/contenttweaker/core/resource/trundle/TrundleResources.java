package com.blamejared.contenttweaker.core.resource.trundle;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.AccessMode;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.NoSuchFileException;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

final class TrundleResources {
    private record AccessFlags(boolean read, boolean write, boolean execute) {
        private static final Set<AccessMode> ALLOWED = Set.of(AccessMode.READ, AccessMode.EXECUTE, AccessMode.WRITE);

        static AccessFlags of(final AccessMode... modes) {
            final Set<AccessMode> modeSet = check(ALLOWED, modes);
            final boolean read = modeSet.contains(AccessMode.READ);
            final boolean write = modeSet.contains(AccessMode.WRITE);
            final boolean execute = modeSet.contains(AccessMode.EXECUTE);
            return new AccessFlags(read, write, execute);
        }
    }

    private record CopyFlags(boolean replaceExisting, boolean copyAttributes) {
        private static final Set<CopyOption> ALLOWED = Set.of(StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);

        static CopyFlags of(final CopyOption... options) {
            final Set<CopyOption> optionSet = check(ALLOWED, options);
            final boolean replaceExisting = optionSet.contains(StandardCopyOption.REPLACE_EXISTING);
            final boolean copyAttributes = optionSet.contains(StandardCopyOption.COPY_ATTRIBUTES);
            return new CopyFlags(replaceExisting, copyAttributes);
        }
    }

    private record MoveFlags(boolean replaceExisting, boolean atomicMove) {
        private static final Set<CopyOption> ALLOWED = Set.of(StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);

        static MoveFlags of(final CopyOption... options) {
            final Set<CopyOption> optionSet = check(ALLOWED, options);
            final boolean replaceExisting = optionSet.contains(StandardCopyOption.REPLACE_EXISTING);
            final boolean atomicMove = optionSet.contains(StandardCopyOption.ATOMIC_MOVE);
            return new MoveFlags(replaceExisting, atomicMove);
        }
    }

    private TrundleResources() {}

    static Void access(final TrundlePathResolutionResult result, final AccessMode... mode) throws IOException {
        final AccessFlags flags = AccessFlags.of(mode);
        final TrundleResource resource = result.element();
        if (resource == null) {
            throw new NoSuchFileException(result.elementName());
        }

        final TrundlePath origin = result.originalPath();
        DosFileAttributes attributes = null;

        // Read permission is implicitly granted
        if (flags.write()) {
            if ((attributes = origin.attributes(DosFileAttributes.class)).isReadOnly()) {
                throw new AccessDeniedException(result.elementName(), null, "File is read only");
            }
        }
        if (flags.execute()) {
            if (!(attributes == null ? (origin.attributes(DosFileAttributes.class)) : attributes).isSystem()) {
                throw new AccessDeniedException(result.elementName(), null, "Cannot execute non-system file"); // TODO("Work in progress: figure out what execute means")
            }
        }

        return null;
    }

    static Void directory(final TrundlePathResolutionResult result, final FileAttribute<?>... attributes) {
        if (result.element() != null) {
            throw new TrundleException(TrundleException.Code.ITEM_ALREADY_EXISTS, result.elementName());
        }
        final TrundleResource parent = result.parent();
        final TrundleDirectory directory = new TrundleDirectory(result.elementName(), new ArrayList<>(Arrays.asList(attributes)));
        parent.add(directory);
        return null;
    }

    static Void delete(final TrundlePathResolutionResult result) throws IOException {
        delete(result, true);
        return null;
    }

    static boolean deleteIfExists(final TrundlePathResolutionResult result) throws IOException {
        return delete(result, false);
    }

    static Void copy(final TrundlePathResolutionResult from, final TrundlePathResolutionResult to, final CopyOption... options) throws IOException {
        final CopyFlags flags = CopyFlags.of(options);
        final TrundleResource source = from.element();
        final TrundleResource maybeDestination = to.element();
        final TrundleResource destinationParent = to.parent();

        if (source == null) {
            throw new TrundleException(TrundleException.Code.RESOLUTION_ERROR, "No such file " + from.elementName());
        }
        if (source == maybeDestination) {
            return null;
        }

        final boolean destinationExists = maybeDestination != null;
        final boolean destinationDirectory = destinationExists && maybeDestination.type() == TrundleResource.Type.DIRECTORY;

        if (destinationExists && !flags.replaceExisting()) {
            throw new FileAlreadyExistsException(maybeDestination.name());
        }
        if (flags.replaceExisting() && destinationDirectory && !((TrundleDirectory) maybeDestination).children().isEmpty()) {
            throw new DirectoryNotEmptyException(maybeDestination.name());
        }

        final List<FileAttribute<?>> attributes = new ArrayList<>(flags.copyAttributes()? source.attributes() : List.of());
        final String name = to.elementName();
        final boolean isDirectoryCopy = source.type() == TrundleResource.Type.DIRECTORY;
        final TrundleResource copy = isDirectoryCopy? new TrundleDirectory(name, attributes) : new TrundleFile(name, source.contents(), attributes);

        if (destinationExists) {
            destinationParent.remove(maybeDestination);
        }
        destinationParent.add(copy);

        return null;
    }

    static Void move(final TrundlePathResolutionResult from, final TrundlePathResolutionResult to, final CopyOption... options) throws IOException {
        final MoveFlags flags = MoveFlags.of(options);
        final TrundleResource source = from.element();
        final TrundleResource sourceParent = from.parent();
        final TrundleResource maybeDestination = to.element();
        final TrundleResource destinationParent = to.parent();

        if (source == null) {
            throw new TrundleException(TrundleException.Code.RESOLUTION_ERROR, "No such file " + from.elementName());
        }
        if (source == maybeDestination) {
            return null;
        }

        final boolean destinationExists = maybeDestination != null;
        final boolean destinationDirectory = destinationExists && maybeDestination.type() == TrundleResource.Type.DIRECTORY;

        if (destinationExists && !flags.replaceExisting()) {
            throw new FileAlreadyExistsException(maybeDestination.name());
        }
        if (flags.replaceExisting() && destinationDirectory && !((TrundleDirectory) maybeDestination).children().isEmpty()) {
            throw new DirectoryNotEmptyException(maybeDestination.name());
        }
        if (from.originalPath().fileStore() != to.originalPath().fileStore()) { // Should never occur if the two paths are part of the same file system
            throw new DirectoryNotEmptyException(source.name());
        }
        if (flags.atomicMove()) {
            throw new AtomicMoveNotSupportedException(source.name(), to.elementName(), "Atomic moves are not yet implemented");
        }

        if (destinationExists) {
            destinationParent.remove(maybeDestination);
        }
        destinationParent.add(source);
        sourceParent.remove(source);

        return null;
    }

    static boolean same(final TrundlePathResolutionResult a, final TrundlePathResolutionResult b) throws IOException {
        final TrundleResource first = a.element();
        final TrundleResource second = b.element();

        if (first == null || second == null) {
            throw new IOException("Unable to check if two files are the same with at least one that does not exist", new NullPointerException());
        }

        return first == second;
    }

    static boolean hidden(final TrundlePathResolutionResult path) throws IOException {
        final TrundleResource resource = path.element();
        if (resource == null) {
            throw new TrundleException(TrundleException.Code.RESOLUTION_ERROR, path.elementName());
        }
        return TrundleAttributes.hidden(path);
    }

    private static boolean delete(final TrundlePathResolutionResult result, final boolean crash) throws IOException {
        final TrundleResource resource = result.element();
        if (resource == null) {
            if (crash) {
                throw new NoSuchFileException(result.elementName());
            } else {
                return false;
            }
        }
        if (resource.type() == TrundleResource.Type.DIRECTORY) {
            final TrundleDirectory directory = (TrundleDirectory) resource;
            if (!directory.children().isEmpty()) {
                throw new DirectoryNotEmptyException(result.elementName());
            }
        }
        final TrundleResource parent = result.parent();
        parent.remove(resource);
        return true;
    }

    @SafeVarargs
    private static <T> Set<T> check(final Set<T> allowed, final T... provided) {
        return check(new HashSet<>(Arrays.asList(provided)), allowed);
    }

    private static <T> Set<T> check(final Set<T> provided, final Set<T> allowed) {
        final Set<T> difference = new HashSet<>(provided);
        difference.removeAll(allowed);
        if (!difference.isEmpty()) {
            throw new UnsupportedOperationException("Unknown option " + difference);
        }
        return provided;
    }
}
