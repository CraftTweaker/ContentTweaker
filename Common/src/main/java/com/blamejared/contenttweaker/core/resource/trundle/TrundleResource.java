package com.blamejared.contenttweaker.core.resource.trundle;

import java.nio.file.attribute.FileAttribute;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

abstract sealed class TrundleResource permits TrundleDirectory, TrundleFile {
    enum Type {
        DIRECTORY,
        FILE
    }

    private final String name;
    private final Type type;
    private final Map<String, FileAttribute<?>> attributes;

    TrundleResource(final String name, final Type type, final Collection<FileAttribute<?>> attributes) {
        this.name = Objects.requireNonNull(name);
        this.type = Objects.requireNonNull(type);
        this.attributes = TrundleFileAttributes.bake(Objects.requireNonNull(attributes).stream().collect(Collectors.toMap(FileAttribute::name, Function.identity())));
    }

    final String name() {
        return this.name;
    }

    final Type type() {
        return this.type;
    }

    final Collection<FileAttribute<?>> attributes() {
        return Collections.unmodifiableCollection(this.attributes.values());
    }

    final TrundleFileAttributes attributesObject() {
        return TrundleFileAttributes.of(this, this.attributes);
    }

    abstract void add(final TrundleResource resource);
    abstract void remove(final TrundleResource resource);
    abstract TrundleResource tryResolve(final String name);
    abstract byte[] contents();
    abstract long size();

    final TrundleResource resolve(final String name) {
        final TrundleResource resource = this.tryResolve(name);
        if (resource == null) {
            throw new TrundleException(TrundleException.Code.RESOLUTION_ERROR, "Unable to resolve child '" + name + "'", new NullPointerException());
        }
        return resource;
    }
}
