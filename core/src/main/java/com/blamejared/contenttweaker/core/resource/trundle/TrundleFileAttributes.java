package com.blamejared.contenttweaker.core.resource.trundle;

import java.io.IOException;
import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

final class TrundleFileAttributes implements DosFileAttributes {
    private interface ModifiableFileAttribute<T> extends FileAttribute<T> {
        void value(final T value);
    }

    private static final class BooleanAttribute implements ModifiableFileAttribute<Boolean> {
        private final String name;
        private boolean value;

        BooleanAttribute(final String name) {
            this(name, false);
        }

        BooleanAttribute(final String name, final boolean initialValue) {
            this.name = Objects.requireNonNull(name);
            this.value = initialValue;
        }

        @Override
        public String name() {
            return this.name;
        }

        @Override
        public Boolean value() {
            return this.value;
        }

        @Override
        public void value(final Boolean value) {
            this.value = value;
        }
    }

    static final String HIDDEN = "h";
    static final String READ_ONLY = "r";

    static final Set<String> SUPPORTED_ATTRIBUTES = Set.of(HIDDEN);

    private static final Map<String, Function<String, ? extends ModifiableFileAttribute<?>>> ATTRIBUTE_CREATORS = Map.of(
            HIDDEN, BooleanAttribute::new
    );

    private final TrundleResource resource;
    private final Map<String, FileAttribute<?>> attributes;

    private TrundleFileAttributes(final TrundleResource resource, final Map<String, FileAttribute<?>> attributes) {
        this.resource = resource;
        this.attributes = attributes;
    }

    static TrundleFileAttributes of(final TrundleResource resource) throws IOException {
        if (resource == null) {
            throw new IOException("Unable to read resource");
        }
        return resource.attributesObject();
    }

    static TrundleFileAttributes of(final TrundleResource resource, final Map<String, FileAttribute<?>> attributes) {
        return new TrundleFileAttributes(Objects.requireNonNull(resource), Objects.requireNonNull(attributes));
    }

    static Map<String, FileAttribute<?>> bake(final Map<String, FileAttribute<?>> original) {
        final Map<String, FileAttribute<?>> baked = new LinkedHashMap<>(original);
        for (final String attribute : SUPPORTED_ATTRIBUTES) {
            baked.computeIfAbsent(attribute, it -> ATTRIBUTE_CREATORS.get(it).apply(it));
        }
        return baked;
    }

    @Override
    public boolean isReadOnly() {
        return false; // TODO()
    }

    @Override
    public boolean isHidden() {
        return this.booleanAttribute(HIDDEN).value();
    }

    @Override
    public boolean isArchive() {
        return false; // TODO()
    }

    @Override
    public boolean isSystem() {
        return false; // TODO()
    }

    @Override
    public FileTime lastModifiedTime() {
        return null; // TODO()
    }

    @Override
    public FileTime lastAccessTime() {
        return null; // TODO()
    }

    @Override
    public FileTime creationTime() {
        return null; // TODO()
    }

    @Override
    public boolean isRegularFile() {
        return this.resource.type() == TrundleResource.Type.FILE;
    }

    @Override
    public boolean isDirectory() {
        return this.resource.type() == TrundleResource.Type.DIRECTORY;
    }

    @Override
    public boolean isSymbolicLink() {
        return false;
    }

    @Override
    public boolean isOther() {
        return false;
    }

    @Override
    public long size() {
        return this.isRegularFile()? this.resource.size() : 0;
    }

    @Override
    public Object fileKey() {
        return null;
    }

    void setHidden(final boolean value) {
        this.booleanAttribute(HIDDEN).value(value);
    }

    void setReadOnly(final boolean value) {
        // TODO()
    }

    void setSystem(final boolean value) {
        // TODO()
    }

    void setArchive(final boolean value) {
        // TODO()
    }

    void setTimes(final FileTime lastModifiedTime, final FileTime lastAccessTime, final FileTime createTime) {
        // TODO()
    }

    Object attribute(final String attribute) {
        Objects.requireNonNull(attribute);
        return this.attributes.get(attribute).value();
    }

    @SuppressWarnings("unchecked")
    <T> void attribute(final String attribute, final T value) {
        Objects.requireNonNull(attribute);
        Objects.requireNonNull(value);

        final FileAttribute<T> fileAttribute = (FileAttribute<T>) this.attributes.get(attribute);
        if (!(fileAttribute instanceof ModifiableFileAttribute<T> modifiableFileAttribute)) {
            throw new IllegalStateException();
        }
        modifiableFileAttribute.value(value);
    }

    @SuppressWarnings("SameParameterValue")
    private BooleanAttribute booleanAttribute(final String name) {
        final FileAttribute<?> attribute = this.attributes.computeIfAbsent(name, BooleanAttribute::new);
        if (!(attribute instanceof BooleanAttribute booleanAttribute)) {
            throw new IllegalStateException();
        }
        return booleanAttribute;
    }
}
