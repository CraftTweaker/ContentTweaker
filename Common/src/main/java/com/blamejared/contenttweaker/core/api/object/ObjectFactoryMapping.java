package com.blamejared.contenttweaker.core.api.object;

import com.blamejared.crafttweaker.api.util.InstantiationUtil;

@FunctionalInterface
public interface ObjectFactoryMapping<T, U extends ObjectFactory<T>> {
    Class<U> type();

    default U of() {
        return InstantiationUtil.getOrCreateInstance(this.type());
    }
}
