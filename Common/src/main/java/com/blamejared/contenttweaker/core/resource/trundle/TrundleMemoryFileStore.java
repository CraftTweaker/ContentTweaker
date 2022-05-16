package com.blamejared.contenttweaker.core.resource.trundle;

import com.blamejared.contenttweaker.core.ContentTweakerCore;
import sun.misc.Unsafe;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.nio.file.FileStore;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.FileStoreAttributeView;
import java.util.Objects;
import java.util.function.IntSupplier;

final class TrundleMemoryFileStore extends FileStore {
    private final String name;
    private final Runtime runtime;
    private final IntSupplier pageSize;

    TrundleMemoryFileStore(final String name) {
        this.name = Objects.requireNonNull(name);
        this.runtime = Runtime.getRuntime();
        this.pageSize = this::queryPageSize;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public String type() {
        return "memory";
    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Override
    public long getTotalSpace() {
        return this.runtime.maxMemory();
    }

    @Override
    public long getUsableSpace() {
        return this.runtime.totalMemory();
    }

    @Override
    public long getUnallocatedSpace() {
        return this.runtime.freeMemory();
    }

    @Override
    public boolean supportsFileAttributeView(final Class<? extends FileAttributeView> type) {
        Objects.requireNonNull(type);
        return type == BasicFileAttributeView.class || type == DosFileAttributeView.class;
    }

    @Override
    public boolean supportsFileAttributeView(final String name) {
        Objects.requireNonNull(name);
        return "basic".equals(name) || "dos".equals(name);
    }

    @Override
    public <V extends FileStoreAttributeView> V getFileStoreAttributeView(final Class<V> type) {
        Objects.requireNonNull(type);
        return null;
    }

    @Override
    public Object getAttribute(final String attribute) {
        return switch (Objects.requireNonNull(attribute)) {
            case "totalSpace" -> this.getTotalSpace();
            case "usableSpace" -> this.getUsableSpace();
            case "unallocatedSpace" -> this.getUnallocatedSpace();
            case "bytesPerSector" -> this.pageSize.getAsInt();
            default -> null;
        };
    }

    private int queryPageSize() {
        try {
            final MethodHandles.Lookup lookup = MethodHandles.privateLookupIn(Unsafe.class, MethodHandles.lookup());
            final VarHandle handle = lookup.findStaticVarHandle(Unsafe.class, "theUnsafe", Unsafe.class);
            final Unsafe unsafe = (Unsafe) handle.get();
            return unsafe.pageSize();
        } catch (final IllegalAccessException | NoSuchFieldException e) {
            ContentTweakerCore.LOGGER.error("Unable to perform unsafe lookup: not a big deal", e);
            return -1;
        }
    }
}
