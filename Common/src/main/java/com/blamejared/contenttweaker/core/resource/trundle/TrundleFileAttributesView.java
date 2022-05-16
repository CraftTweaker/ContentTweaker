package com.blamejared.contenttweaker.core.resource.trundle;

import java.io.IOException;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.FileTime;
import java.util.Objects;

final class TrundleFileAttributesView implements DosFileAttributeView {
    @FunctionalInterface
    private interface ThrowingSupplier<T, E extends Exception> {
        T get() throws E;
    }

    private static final class MemoizingThrowingSupplier<T, E extends Exception> implements ThrowingSupplier<T, E> {
        private volatile ThrowingSupplier<T, E> delegate;
        private volatile boolean initialized;
        private T result;

        private MemoizingThrowingSupplier(final ThrowingSupplier<T, E> delegate) {
            this.delegate = Objects.requireNonNull(delegate);
            this.initialized = false;
            this.result = null;
        }

        @Override
        public T get() throws E {
            if (!this.initialized) {
                synchronized (this) {
                    if (!this.initialized) {
                        final T result = this.delegate.get();
                        this.initialized = true;
                        this.result = result;
                        this.delegate = null;
                    }
                }
            }
            return this.result;
        }
    }

    private final String name;
    private final ThrowingSupplier<TrundleFileAttributes, IOException> attributes;

    private TrundleFileAttributesView(final String name, final ThrowingSupplier<TrundleFileAttributes, IOException> attributes) {
        this.name = name;
        this.attributes = new MemoizingThrowingSupplier<>(attributes);
    }

    static <A extends FileAttributeView> TrundleFileAttributesView of(final TrundleResource resource, final Class<A> type) {
        Objects.requireNonNull(resource);
        Objects.requireNonNull(type);
        return new TrundleFileAttributesView(classToName(type), () -> TrundleFileAttributes.of(resource));
    }

    private static <A extends FileAttributeView> String classToName(final Class<A> type) {
        if (type == BasicFileAttributeView.class) {
            return "basic";
        }
        if (type == DosFileAttributeView.class) {
            return "dos";
        }
        throw new IllegalArgumentException(type.getName());
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public DosFileAttributes readAttributes() throws IOException {
        return this.attributes.get();
    }

    @Override
    public void setReadOnly(final boolean value) throws IOException {
        this.attributes.get().setReadOnly(value);
    }

    @Override
    public void setHidden(final boolean value) throws IOException {
        this.attributes.get().setHidden(value);
    }

    @Override
    public void setSystem(final boolean value) throws IOException {
        this.attributes.get().setSystem(value);
    }

    @Override
    public void setArchive(final boolean value) throws IOException {
        this.attributes.get().setArchive(value);
    }

    @Override
    public void setTimes(final FileTime lastModifiedTime, final FileTime lastAccessTime, final FileTime createTime) throws IOException {
        this.attributes.get().setTimes(lastModifiedTime, lastAccessTime, createTime);
    }
}
