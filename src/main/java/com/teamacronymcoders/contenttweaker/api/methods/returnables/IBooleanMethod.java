package com.teamacronymcoders.contenttweaker.api.methods.returnables;

import com.teamacronymcoders.contenttweaker.api.methods.MethodParameters;

@FunctionalInterface
public interface IBooleanMethod {
    Boolean call(MethodParameters methodParameters);
}
