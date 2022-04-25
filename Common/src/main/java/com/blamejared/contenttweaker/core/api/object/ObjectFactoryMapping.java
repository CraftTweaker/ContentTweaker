package com.blamejared.contenttweaker.core.api.object;

@FunctionalInterface
public interface ObjectFactoryMapping<T, U extends ObjectFactory<T>> {
    Class<U> type();

    default U of() {
        try {
            return this.type().getConstructor().newInstance();
        } catch (final ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}
