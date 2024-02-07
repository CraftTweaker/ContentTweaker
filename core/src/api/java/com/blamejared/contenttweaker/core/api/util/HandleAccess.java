package com.blamejared.contenttweaker.core.api.util;

import java.util.Objects;

public final class HandleAccess {
    public enum AccessType {
        STATIC,
        VIRTUAL,
        DIRECT,
        SPECIAL,
        CONSTRUCTOR
    }

    private final AccessType accessType;
    private final Class<?> specialCaller;

    private HandleAccess(final AccessType accessType, final Class<?> specialCaller) {
        this.accessType = Objects.requireNonNull(accessType);
        this.specialCaller = specialCaller;
    }

    public static HandleAccess staticAccess() {
        return new HandleAccess(AccessType.STATIC, null);
    }

    public static HandleAccess virtualAccess() {
        return new HandleAccess(AccessType.VIRTUAL, null);
    }

    public static HandleAccess directAccess() {
        return new HandleAccess(AccessType.DIRECT, null);
    }

    public static HandleAccess specialAccess(final Class<?> specialCaller) {
        return new HandleAccess(AccessType.SPECIAL, Objects.requireNonNull(specialCaller));
    }

    public static HandleAccess constructorAccess() {
        return new HandleAccess(AccessType.CONSTRUCTOR, null);
    }

    public AccessType type() {
        return this.accessType;
    }

    public Class<?> parameter() {
        return this.specialCaller;
    }
}
