package com.teamacronymcoders.contenttweaker.api.methods.returnables;

import com.teamacronymcoders.contenttweaker.api.methods.MethodParameters;

public class StringMethod implements IStringMethod {
    private String string;

    public StringMethod(String string) {
        this.string = string;
    }

    @Override
    public String call(MethodParameters methodParameters) {
        return string;
    }
}
