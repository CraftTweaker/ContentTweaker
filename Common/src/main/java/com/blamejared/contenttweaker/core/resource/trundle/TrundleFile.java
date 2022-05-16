package com.blamejared.contenttweaker.core.resource.trundle;

import java.nio.file.attribute.FileAttribute;
import java.util.List;
import java.util.Objects;

final class TrundleFile extends TrundleResource {
    private byte[] contents;

    TrundleFile(final String name, final List<FileAttribute<?>> attributes) {
        this(name, new byte[0], attributes);
    }

    TrundleFile(final String name, final byte[] contents, final List<FileAttribute<?>> attributes) {
        super(name, Type.FILE, attributes);
        this.contents = Objects.requireNonNull(contents);
    }

    @Override
    void add(final TrundleResource resource) {
        Objects.requireNonNull(resource);
        throw new TrundleException(TrundleException.Code.ADD_NOT_SUPPORTED, this.name() + " is not a directory");
    }

    @Override
    void remove(final TrundleResource resource) {
        Objects.requireNonNull(resource);
        throw new TrundleException(TrundleException.Code.REMOVE_NOT_SUPPORTED, this.name() + " is not a directory");
    }

    @Override
    TrundleResource tryResolve(final String name) {
        Objects.requireNonNull(name);
        throw new TrundleException(TrundleException.Code.ENUMERATION_NOT_SUPPORTED, this.name() + " is not a directory");
    }

    @Override
    byte[] contents() {
        return this.contents;
    }

    @Override
    long size() {
        return this.contents().length;
    }

    byte[] resizeContents(final int amount) {
        if (amount != 0) {
            final byte[] oldContents = this.contents;
            final byte[] newContents = new byte[oldContents.length + amount];
            final int length = Math.min(oldContents.length, newContents.length);
            System.arraycopy(oldContents, 0, newContents, 0, length);
            this.contents = newContents;
        }
        return this.contents();
    }
}
