package com.teamacronymcoders.contenttweaker.api.methods.returnables;

import com.teamacronymcoders.contenttweaker.api.methods.MethodParameters;

public class FloatMethod implements IFloatMethod {
    private Float aFloat;

    public FloatMethod(Float aFloat) {
        this.aFloat = aFloat;
    }

    @Override
    public Float call(MethodParameters methodParameters) {
        return aFloat;
    }
}
