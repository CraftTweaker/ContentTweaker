package com.teamacronymcoders.contenttweaker.api.ctobjects.enums;

import stanhebben.zenscript.annotations.ZenMemberGetter;

import java.util.Arrays;

public class EnumWrapper<E extends Enum<E>> {
    private final Class<E> enumClass;

    public EnumWrapper(Class<E> enumClass) {
        this.enumClass = enumClass;
    }

    public EnumValueWrapper[] values() {
        return Arrays.stream(this.enumClass.getEnumConstants())
                .map(EnumValueWrapper::new)
                .toArray(EnumValueWrapper[]::new);
    }

    @ZenMemberGetter
    public EnumValueWrapper<E> getValue(String name) {
        return Arrays.stream(enumClass.getEnumConstants())
                .filter(e -> e.name().equalsIgnoreCase(name))
                .findFirst()
                .map(EnumValueWrapper::new)
                .orElseThrow(() -> new IllegalArgumentException("No Enum Value found for name: " + name));
    }
}
