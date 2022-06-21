package com.blamejared.contenttweaker.core.resource.trundle;

enum TrundlePathType {
    ABSOLUTE,
    RELATIVE;

    boolean isAbsolute() {
        return this == ABSOLUTE;
    }
}
