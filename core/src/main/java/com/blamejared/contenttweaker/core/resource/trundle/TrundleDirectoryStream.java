package com.blamejared.contenttweaker.core.resource.trundle;

import java.io.IOException;
import java.nio.file.ClosedFileSystemException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

final class TrundleDirectoryStream implements DirectoryStream<Path> {
    private static final class TrundleDirectoryStreamIterator implements Iterator<Path> {
        private TrundleFileSystem fs;
        private TrundlePath parent;
        private Iterator<Map.Entry<String, TrundleResource>> directoryIterator;
        private Filter<? super Path> filter;
        private boolean closed;
        private Path nextEntry;

        TrundleDirectoryStreamIterator(
                final TrundleFileSystem fs,
                final TrundlePath parent,
                final Iterator<Map.Entry<String, TrundleResource>> directoryIterator,
                final Filter<? super Path> filter
        ) {
            this.fs = fs;
            this.parent = parent;
            this.directoryIterator = directoryIterator;
            this.filter = filter;
            this.closed = false;
            this.nextEntry = null;
        }

        @Override
        public boolean hasNext() {
            if (this.nextEntry != null) {
                return true;
            }
            try {
                this.computeNextEntry();
            } catch (final IOException e) {
                throw new DirectoryIteratorException(e);
            }
            return this.nextEntry != null;
        }

        @Override
        public Path next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            final Path next = this.nextEntry;
            this.nextEntry = null;
            return next;
        }

        void close() {
            this.fs = null;
            this.parent = null;
            this.directoryIterator = null;
            this.filter = null;
            this.nextEntry = null;
            this.closed = true;
        }

        private void computeNextEntry() throws IOException {
            this.nextEntry = null;
            if (this.closed) {
                return;
            }
            while (this.directoryIterator.hasNext()) {
                final Map.Entry<String, TrundleResource> next = this.directoryIterator.next();
                final TrundlePath path = this.from(next);
                if (this.filter.accept(path)) {
                    this.nextEntry = path;
                    break;
                }
            }
        }

        private TrundlePath from(final Map.Entry<String, TrundleResource> entry) {
            return (TrundlePath) (this.parent == null? this.fs.getPath(entry.getKey()) : this.parent.resolve(entry.getKey()));
        }
    }

    private TrundleDirectoryStreamIterator iterator;
    private boolean gaveIterator;
    private boolean closed;

    private TrundleDirectoryStream(final TrundleDirectoryStreamIterator iterator) {
        this.iterator = iterator;
        this.gaveIterator = false;
        this.closed = false;
    }

    static TrundleDirectoryStream of(final TrundleFileSystem fs, final TrundlePathResolutionResult result, final Filter<? super Path> filter) throws IOException {
        Objects.requireNonNull(fs);
        Objects.requireNonNull(result);
        Objects.requireNonNull(filter);
        final TrundleResource target = result.element();
        if (target == null) {
            throw new TrundleException(TrundleException.Code.RESOLUTION_ERROR, "No such directory " + result);
        }
        if (target.type() != TrundleResource.Type.DIRECTORY) {
            throw new NotDirectoryException(result.elementName());
        }
        final TrundlePath parent = result.originalPath();
        final Iterator<Map.Entry<String, TrundleResource>> entries = ((TrundleDirectory) target).children().entrySet().iterator();
        final TrundleDirectoryStreamIterator iterator = new TrundleDirectoryStreamIterator(fs, parent, entries, filter);
        return new TrundleDirectoryStream(iterator);
    }

    @Override
    public Iterator<Path> iterator() {
        if (this.closed) {
            throw new ClosedFileSystemException();
        }
        if (this.gaveIterator) {
            throw new IllegalStateException();
        }
        this.gaveIterator = true;
        return this.iterator;
    }

    @Override
    public void close() {
        if (this.closed) {
            return;
        }
        this.iterator.close();
        this.iterator = null;
        this.closed = true;
    }
}
