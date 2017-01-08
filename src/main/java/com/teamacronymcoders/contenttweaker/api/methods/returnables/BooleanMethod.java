package com.teamacronymcoders.contenttweaker.api.methods.returnables;

import com.teamacronymcoders.contenttweaker.api.methods.MethodParameters;

public class BooleanMethod implements IBooleanMethod {
    private Boolean aBoolean;

    public BooleanMethod(Boolean aBoolean) {
        this.aBoolean = aBoolean;
    }

    @Override
    public Boolean call(MethodParameters methodParameters) {
        return aBoolean;
    }
}
