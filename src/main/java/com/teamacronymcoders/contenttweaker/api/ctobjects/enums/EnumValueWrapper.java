package com.teamacronymcoders.contenttweaker.api.ctobjects.enums;

import stanhebben.zenscript.annotations.OperatorType;
import stanhebben.zenscript.annotations.ZenOperator;

public class EnumValueWrapper<E extends Enum<E>> {
    private final Enum<E> value;

    public EnumValueWrapper(Enum<E> value) {
        this.value = value;
    }

    @ZenOperator(OperatorType.COMPARE)
    public int compare(EnumValueWrapper enumValueWrapper) {
        if (enumValueWrapper.value.getClass() != this.value.getClass() &&
            this.value.getDeclaringClass() != enumValueWrapper.value.getDeclaringClass()) {
            throw new IllegalArgumentException("Cannot Compare Different Classes");
        }
        return this.value.ordinal() - enumValueWrapper.value.ordinal();
    }
}
