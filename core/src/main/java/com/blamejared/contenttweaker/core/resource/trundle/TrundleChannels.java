package com.blamejared.contenttweaker.core.resource.trundle;

import java.io.IOException;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;

final class TrundleChannels {
    private record Flags(boolean read, boolean write, boolean append, boolean truncate, boolean failIfPresent) {
        static Flags of(final Set<? extends OpenOption> options) {
            final boolean read = options.contains(StandardOpenOption.READ) || shouldDefault(options);
            final boolean write = options.contains(StandardOpenOption.WRITE) || options.contains(StandardOpenOption.APPEND);
            final boolean append = options.contains(StandardOpenOption.APPEND);
            final boolean truncate = options.contains(StandardOpenOption.TRUNCATE_EXISTING);
            final boolean failIfPresent = options.contains(StandardOpenOption.CREATE_NEW);
            final Flags flags = new Flags(read, write, append, truncate, failIfPresent);
            flags.validate(options);
            return flags;
        }

        private static boolean shouldDefault(final Set<? extends OpenOption> options) {
            return !options.contains(StandardOpenOption.READ) && !options.contains(StandardOpenOption.WRITE) && !options.contains(StandardOpenOption.APPEND);
        }

        boolean failIfAbsent() {
            return this.read && !this.write && !this.append;
        }

        private void validate(final Set<? extends OpenOption> options) {
            if (this.append() && (this.read() || this.truncate())) {
                throw new IllegalArgumentException("Illegal combination of attributes");
            }
            if (options.contains(StandardOpenOption.DSYNC) || options.contains(StandardOpenOption.SYNC)) {
                throw new UnsupportedOperationException("Unsupported synchronous I/O");
            }
        }
    }

    private TrundleChannels() {}

    static SeekableByteChannel seekableByteChannel(
            final TrundlePathResolutionResult path,
            final Set<? extends OpenOption> openOptions,
            final FileAttribute<?>... attributes
    ) throws IOException {
        return path.originalPath().fileChannel(openOptions, attributes);
    }

    static FileChannel fileChannel(
            final TrundlePathResolutionResult path,
            final Set<? extends OpenOption> openOptions,
            final FileAttribute<?>... attributes
    ) throws IOException {
        final Flags flags = Flags.of(openOptions);
        final TrundleResource file = path.element();
        final boolean fileExists = Objects.nonNull(file);

        if (fileExists && flags.failIfPresent()) {
            throw new FileAlreadyExistsException(path.elementName());
        }

        if (!fileExists && flags.failIfAbsent()) {
            throw new TrundleException(TrundleException.Code.RESOLUTION_ERROR, "No such file: " + path.originalPath());
        }

        if (fileExists && file.type() != TrundleResource.Type.FILE) {
            throw new TrundleException(TrundleException.Code.READ_NOT_SUPPORTED, "Unable to read " + path.originalPath());
        }

        if (!fileExists) {
            final TrundleResource parent = path.parent();
            final TrundleFile newlyCreatedFile = new TrundleFile(path.elementName(), new ArrayList<>(Arrays.asList(attributes)));
            parent.add(newlyCreatedFile);
        }

        final TrundleResource actualFile = fileExists? file : path.parent().resolve(path.elementName());
        final long position = flags.append()? actualFile.size() : 0L;
        return new TrundleFileChannel((TrundleFile) actualFile, flags.read(), flags.write(), position);
    }

    @SuppressWarnings("unused")
    static AsynchronousFileChannel asyncFileChannel(
            final TrundlePathResolutionResult path,
            final Set<? extends OpenOption> options,
            final ExecutorService executor,
            final FileAttribute<?>... attributes
    ) throws IOException {
        throw new IOException(new UnsupportedOperationException("Not yet implemented"));
    }
}
