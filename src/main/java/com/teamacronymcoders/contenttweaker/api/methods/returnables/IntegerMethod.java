package com.teamacronymcoders.contenttweaker.api.methods.returnables;

import com.teamacronymcoders.contenttweaker.api.methods.MethodParameters;

public class IntegerMethod implements IIntegerMethod {
    private Integer integer;

    public IntegerMethod(Integer integer) {
        this.integer = integer;
    }

    @Override
    public Integer call(MethodParameters methodParameters) {
        return this.integer;
    }
}
