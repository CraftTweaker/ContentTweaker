package com.blamejared.contenttweaker.core.resource;

import com.blamejared.contenttweaker.core.api.resource.ResourceTemplateHelper;
import com.blamejared.contenttweaker.core.api.resource.StandardResourceSerializers;
import com.blamejared.contenttweaker.core.service.ServiceManager;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.annotation.Target;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

final class TemplateHelper implements ResourceTemplateHelper {
    private TemplateHelper() {}

    static TemplateHelper of() {
        return new TemplateHelper();
    }

    @Override
    public void provideFrom(final String templatePath, final String fragmentPath, final Provider provider) {
        Objects.requireNonNull(templatePath);
        Objects.requireNonNull(fragmentPath);
        Objects.requireNonNull(provider);
        try {
            final Path base = ServiceManager.platform().locateResource("meta", "template");
            final Path target = base.resolve(templatePath);
            this.provideFromChecked(target, fragmentPath, provider);
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void provideFrom(final Path templatePath, final String fragmentPath, final Provider provider) {
        Objects.requireNonNull(templatePath);
        Objects.requireNonNull(fragmentPath);
        Objects.requireNonNull(provider);
        try {
            this.provideFromChecked(templatePath, fragmentPath, provider);
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void provideFromChecked(final Path templatePath, final String fragmentPath, final Provider provider) throws IOException {
        if (!Files.exists(templatePath)) {
            throw new IOException("Unable to read template " + templatePath + " as it does not exist");
        }
        final byte[] bytes = Files.readAllBytes(templatePath);
        provider.provide(fragmentPath, bytes, StandardResourceSerializers.BYTE_ARRAY);
    }
}
