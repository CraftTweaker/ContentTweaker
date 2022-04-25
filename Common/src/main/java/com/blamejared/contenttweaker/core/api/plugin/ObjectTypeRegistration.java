package com.blamejared.contenttweaker.core.api.plugin;

import com.blamejared.contenttweaker.core.api.object.ObjectType;

public interface ObjectTypeRegistration {
    <T> void registerType(final ObjectType<T> type);
}
