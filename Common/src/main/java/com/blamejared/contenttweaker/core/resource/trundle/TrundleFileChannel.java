package com.blamejared.contenttweaker.core.resource.trundle;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.NonReadableChannelException;
import java.nio.channels.NonWritableChannelException;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

final class TrundleFileChannel extends FileChannel {
    private final Lock lock;
    private final TrundleFile resource;
    private final boolean read;
    private final boolean write;
    private final boolean appending;

    private long position;

    TrundleFileChannel(final TrundleFile resource, final boolean read, final boolean write, final long position) {
        this.lock = new ReentrantLock(true);
        this.resource = resource;
        this.read = read;
        this.write = write;
        this.position = position;
        this.appending = position != 0L;
    }

    @Override
    public int read(final ByteBuffer dst) throws IOException {
        Objects.requireNonNull(dst);
        if (dst.isReadOnly()) {
            throw new IllegalArgumentException("Unable to fill a read-only buffer");
        }
        if (!this.read) {
            throw new NonReadableChannelException();
        }
        this.checkOpen();
        this.lock.lock();
        try {
            final byte[] contents = this.resource.contents();
            final int available = contents.length - (int) this.position;

            if (available <= 0) { // We reached the end of the file, at the moment
                return -1;
            }

            final int remaining = dst.remaining();
            final int readAmount = Math.min(available, remaining);
            dst.put(contents, (int) this.position, readAmount);

            this.position += readAmount;
            return readAmount;
        } catch (final BufferOverflowException | IndexOutOfBoundsException e) {
            throw new IOException("Unable to read from resource into buffer", e);
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public long read(final ByteBuffer[] dsts, final int offset, final int length) throws IOException {
        Objects.requireNonNull(dsts);
        Objects.checkFromIndexSize(offset, length, dsts.length);
        for (final ByteBuffer buffer : dsts) {
            if (buffer.isReadOnly()) {
                throw new IllegalArgumentException("Unable to fill a read-only buffer");
            }
        }
        if (!this.read) {
            throw new NonReadableChannelException();
        }
        this.checkOpen();
        this.lock.lock();
        try {
            final byte[] contents = this.resource.contents();
            int available = contents.length - (int) this.position;

            if (available <= 0) {
                return -1;
            }

            long rollingTotal = 0;

            for (int i = offset, l = offset + length; i < l; ++i) {
                final ByteBuffer buffer = dsts[i];
                final int remainingForBuffer = buffer.remaining();
                final int readAmount = Math.min(available, remainingForBuffer);
                buffer.put(contents, (int) this.position, readAmount);
                this.position += readAmount;
                rollingTotal += readAmount;
                available = contents.length - (int) this.position;
            }

            return rollingTotal;
        } catch (final BufferOverflowException | IndexOutOfBoundsException e) {
            throw new IOException("Unable to read from resource into buffers", e);
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public int write(final ByteBuffer src) throws IOException {
        Objects.requireNonNull(src);
        if (!this.write) {
            throw new NonWritableChannelException();
        }
        this.checkOpen();
        this.lock.lock();
        final long savedPosition = this.position;
        try {
            final byte[] contents = this.resource.contents();
            if (this.appending) {
                this.position = contents.length;
            }

            final int remaining = src.remaining();
            final int available = contents.length - (int) this.position;

            final byte[] newContents = available < remaining? this.resource.resizeContents(remaining - available) : contents;

            src.get(newContents, (int) this.position, remaining);
            this.position += remaining;
            return remaining;
        } catch (final BufferUnderflowException | IndexOutOfBoundsException e) {
            this.position = savedPosition;
            throw new IOException("Unable to write data to the resource", e);
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public long write(final ByteBuffer[] srcs, final int offset, final int length) throws IOException {
        Objects.requireNonNull(srcs);
        Objects.checkFromIndexSize(offset, length, srcs.length);
        if (!this.write) {
            throw new NonWritableChannelException();
        }
        this.checkOpen();
        this.lock.lock();
        final long savedPosition = this.position;
        try {
            final byte[] contents = this.resource.contents();
            if (this.appending) {
                this.position = contents.length;
            }

            int available = contents.length - (int) this.position;
            byte[] newContents = contents;
            long rollingTotal = 0;

            for (int i = offset, l = offset + length; i < l; ++i) {
                final ByteBuffer buffer = srcs[i];
                final int remainingForBuffer = buffer.remaining();
                if (available < remainingForBuffer) {
                    final int increase = remainingForBuffer - available;
                    newContents = this.resource.resizeContents(increase);
                }

                buffer.get(newContents, (int) this.position, remainingForBuffer);
                this.position += remainingForBuffer;
                rollingTotal += remainingForBuffer;
                available = contents.length - (int) this.position;
            }

            return rollingTotal;
        } catch (final BufferUnderflowException | IndexOutOfBoundsException e) {
            this.position = savedPosition;
            throw new IOException("Unable to write data to the resource", e);
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public long position() throws IOException {
        this.checkOpen();
        return this.position;
    }

    @Override
    public FileChannel position(final long newPosition) throws IOException {
        if (newPosition < 0) {
            throw new IllegalArgumentException("Unable to set a negative position");
        }
        this.checkOpen();
        this.lock.lock();
        try {
            this.position = newPosition;
            return this;
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public long size() throws IOException {
        this.checkOpen();
        return this.resource.size();
    }

    @Override
    public FileChannel truncate(final long size) throws IOException {
        if (size < 0) {
            throw new IllegalArgumentException("Unable to truncate to a negative size");
        }
        if (!this.write) {
            throw new NonWritableChannelException();
        }
        this.checkOpen();
        if (this.appending) {
            throw new IOException("The resource cannot be truncated");
        }
        this.lock.lock();
        try {
            final byte[] contents = this.resource.contents();
            final int currentSize = contents.length;

            if (currentSize > size) {
                final int reduce = (int) size - currentSize;
                this.resource.resizeContents(reduce);
            }

            return this;
        } catch (final IndexOutOfBoundsException e) {
            throw new IOException("Unable to truncate resource", e);
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public void force(final boolean metaData) throws IOException {
        this.checkOpen();
        // Do nothing: everything is already forcefully written to our storage
    }

    @Override
    public long transferTo(final long position, final long count, final WritableByteChannel target) throws IOException {
        Objects.requireNonNull(target);
        if (position < 0 || count < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (!this.read) {
            throw new NonReadableChannelException();
        }
        this.checkOpen();
        this.lock.lock();
        try {
            final ByteBuffer readBuffer = ByteBuffer.allocate((int) count).order(ByteOrder.nativeOrder());
            final byte[] contents = this.resource.contents();
            final long available = contents.length - position;
            final int readAmount = (int) Math.min(available, count);
            readBuffer.put(contents, (int) position, readAmount);
            readBuffer.flip();
            return target.write(readBuffer);
        } catch (final BufferOverflowException | IndexOutOfBoundsException e) {
            throw new IOException("Unable to transfer from resource to channel", e);
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public long transferFrom(final ReadableByteChannel src, final long position, final long count) throws IOException {
        Objects.requireNonNull(src);
        if (position < 0 || count < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (!this.write) {
            throw new NonWritableChannelException();
        }
        this.checkOpen();
        this.lock.lock();
        try {
            final byte[] contents = this.resource.contents();
            if (this.appending && this.position != contents.length) {
                throw new IOException("Resource is opened in append mode, but position is incompatible");
            }

            final ByteBuffer writeBuffer = ByteBuffer.allocate((int) count).order(ByteOrder.nativeOrder());
            src.read(writeBuffer);
            writeBuffer.flip();

            final int remaining = writeBuffer.remaining();
            final int available = (int) (contents.length - position);
            final byte[] newContents = available < remaining? this.resource.resizeContents(remaining - available) : contents;
            writeBuffer.get(newContents, (int) position, remaining);
            return remaining;
        } catch (final BufferUnderflowException | IndexOutOfBoundsException e) {
            throw new IOException("Unable to transfer from channel to resource", e);
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public int read(final ByteBuffer dst, final long position) throws IOException {
        Objects.requireNonNull(dst);
        if (dst.isReadOnly()) {
            throw new IllegalArgumentException("Unable to fill a read-only buffer");
        }
        if (position < 0) {
            throw new IllegalArgumentException("Invalid position");
        }
        if (!this.read) {
            throw new NonReadableChannelException();
        }
        this.checkOpen();
        this.lock.lock();
        try {
            final byte[] contents = this.resource.contents();
            final int available = contents.length - (int) position;

            if (available <= 0) { // We reached the end of the file, at the moment
                return -1;
            }

            final int remaining = dst.remaining();
            final int readAmount = Math.min(available, remaining);
            dst.put(contents, (int) position, readAmount);
            return readAmount;
        } catch (final BufferOverflowException | IndexOutOfBoundsException e) {
            throw new IOException("Unable to read from resource into buffer", e);
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public int write(final ByteBuffer src, final long position) throws IOException {
        Objects.requireNonNull(src);
        if (position < 0) {
            throw new IllegalArgumentException("Invalid position");
        }
        if (!this.write) {
            throw new NonWritableChannelException();
        }
        this.checkOpen();
        this.lock.lock();
        try {
            final byte[] contents = this.resource.contents();
            if (this.appending && position != contents.length) {
                throw new IOException("Resource is opened in append mode, but position is incompatible");
            }

            final int remaining = src.remaining();
            final int available = contents.length - (int) position;

            final byte[] newContents = available < remaining? this.resource.resizeContents(remaining - available) : contents;

            src.get(newContents, (int) position, remaining);
            return remaining;
        } catch (final BufferUnderflowException | IndexOutOfBoundsException e) {
            throw new IOException("Unable to write data to the resource", e);
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public MappedByteBuffer map(final MapMode mode, final long position, final long size) throws IOException {
        Objects.requireNonNull(mode);
        if (position < 0 || size < 0 || size > Integer.MAX_VALUE) {
            throw new IllegalArgumentException();
        }
        throw new IOException(new UnsupportedOperationException("Not yet implemented"));
    }

    @Override
    public FileLock lock(final long position, final long size, final boolean shared) throws IOException {
        if (position < 0 || size < 0 || (position + size) < 0) {
            throw new IllegalArgumentException();
        }
        throw new IOException(new UnsupportedOperationException("Not yet implemented"));
    }

    @Override
    public FileLock tryLock(final long position, final long size, final boolean shared) throws IOException {
        if (position < 0 || size < 0 || (position + size) < 0) {
            throw new IllegalArgumentException();
        }
        throw new IOException(new UnsupportedOperationException("Not yet implemented"));
    }

    @Override
    protected void implCloseChannel() {
        // NO-OP
    }

    private void checkOpen() throws IOException {
        if (this.closed()) {
            throw new ClosedChannelException();
        }
    }

    private boolean closed() {
        return !this.isOpen();
    }
}
