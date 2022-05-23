package com.blamejared.contenttweaker.core.api.resource;

import java.nio.file.Path;
import java.util.Objects;

public interface ResourceTemplateHelper {
    @FunctionalInterface
    interface Provider {
        void provide(final String path, final byte[] data, final ResourceSerializer<byte[]> serializer);
    }

    void provideFrom(final String templatePath, final String fragmentPath, final Provider provider);
    void provideFrom(final Path templatePath, final String fragmentPath, final Provider provider);

    default void provideFrom(final String templatePath, final String fragmentPath, final ResourceFragment fragment) {
        this.provideFrom(templatePath, fragmentPath, Objects.requireNonNull(fragment)::provideFixed);
    }

    default void provideFrom(final Path templatePath, final String fragmentPath, final ResourceFragment fragment) {
        this.provideFrom(templatePath, fragmentPath, Objects.requireNonNull(fragment)::provideFixed);
    }
}
