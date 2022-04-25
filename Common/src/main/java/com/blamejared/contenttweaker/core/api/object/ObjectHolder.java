package com.blamejared.contenttweaker.core.api.object;

import java.util.function.Supplier;

public interface ObjectHolder<T> extends Supplier<T> {
    ObjectType<T> type();
    @Override T get();
}
