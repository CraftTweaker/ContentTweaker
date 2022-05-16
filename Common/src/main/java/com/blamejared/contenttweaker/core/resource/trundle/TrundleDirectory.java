package com.blamejared.contenttweaker.core.resource.trundle;

import java.nio.file.attribute.FileAttribute;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

final class TrundleDirectory extends TrundleResource {
    private final Map<String, TrundleResource> children;

    TrundleDirectory(final String name, final List<FileAttribute<?>> attributes) {
        super(name, Type.DIRECTORY, attributes);
        this.children = new LinkedHashMap<>();
    }

    @Override
    void add(final TrundleResource resource) {
        Objects.requireNonNull(resource);
        final String name = resource.name();
        if (this.children.containsKey(name)) {
            throw new TrundleException(TrundleException.Code.ITEM_ALREADY_EXISTS, name);
        }
        this.children.put(name, resource);
    }

    @Override
    void remove(final TrundleResource resource) {
        Objects.requireNonNull(resource);
        final String name = resource.name();
        if (!this.children.containsKey(name)) {
            throw new TrundleException(TrundleException.Code.RESOLUTION_ERROR, name);
        }
        if (!this.children.remove(name, resource)) {
            throw new TrundleException(TrundleException.Code.RESOLUTION_ERROR, name);
        }
    }

    @Override
    TrundleResource tryResolve(final String name) {
        Objects.requireNonNull(name);
        return this.children.get(name);
    }

    @Override
    byte[] contents() {
        throw new TrundleException(TrundleException.Code.READ_NOT_SUPPORTED, this.name() + " is not a file");
    }

    @Override
    long size() {
        throw new TrundleException(TrundleException.Code.READ_NOT_SUPPORTED, this.name() + " is not a file");
    }

    Map<String, TrundleResource> children() {
        return this.children;
    }
}
