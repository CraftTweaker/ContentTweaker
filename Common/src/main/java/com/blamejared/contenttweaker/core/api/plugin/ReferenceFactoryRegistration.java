package com.blamejared.contenttweaker.core.api.plugin;

import com.blamejared.contenttweaker.core.api.object.ObjectType;
import com.blamejared.contenttweaker.core.api.object.ReferenceFactory;
import com.blamejared.contenttweaker.core.api.zen.object.Reference;

public interface ReferenceFactoryRegistration {
    <T, U extends Reference<T>> void register(final ObjectType<T> type, final ReferenceFactory<T, U> factory);
}
