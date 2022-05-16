package com.blamejared.contenttweaker.core.resource.trundle;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

final class TrundleException extends RuntimeException {
    enum Code {
        ADD_NOT_SUPPORTED("Access Denied"),
        ENUMERATION_NOT_SUPPORTED("Access Denied"),
        ITEM_ALREADY_EXISTS("Item Already Exists"),
        READ_NOT_SUPPORTED("Access Denied"),
        REMOVE_NOT_SUPPORTED("Access Denied"),
        RESOLUTION_ERROR("Invalid Descriptor");

        private final String ioMessage;

        Code(final String ioMessage) {
            this.ioMessage = ioMessage;
        }

        String ioMessage() {
            return this.ioMessage;
        }
    }

    private final Code code;

    TrundleException(final Code code, final String message) {
        super(message);
        this.code = code;
    }

    TrundleException(final Code code, final String message, final Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    Code code() {
        return this.code;
    }

    IOException asIoException() {
        if (this.code() == Code.ITEM_ALREADY_EXISTS) {
            return new FileAlreadyExistsException(this.getMessage());
        }

        final StringBuilder message = new StringBuilder(this.code().ioMessage());
        if (this.getMessage() != null) {
            message.append(": ").append(this.getMessage());
        }
        return new IOException(message.toString(), this);
    }
}
