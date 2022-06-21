package com.blamejared.contenttweaker.core.resource;

import java.util.Objects;

final class GeneratedResource<T> {
    @FunctionalInterface
    interface Generator<T> {
        byte[] from(final T data);
    }

    private volatile Generator<T> generator;
    private volatile byte[] data;
    private volatile T resource;

    private GeneratedResource(final Generator<T> generator, final T resource) {
        this.generator = generator;
        this.resource = resource;
        this.data = null;
    }

    public static <T> GeneratedResource<T> of(final T resource, final Generator<T> generator) {
        Objects.requireNonNull(resource);
        Objects.requireNonNull(generator);
        return new GeneratedResource<>(generator, resource);
    }

    byte[] data() {
        if (this.data == null) {
            synchronized (this) {
                if (this.data == null) {
                    this.data = this.generator.from(this.resource);
                    if (this.data == null) {
                        throw new IllegalStateException("Generation of resource failed");
                    }
                    this.generator = null;
                    this.resource = null;
                }
            }
        }
        return this.data;
    }
}
