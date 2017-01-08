package com.teamacronymcoders.contenttweaker.api.methods.returnables;

import com.teamacronymcoders.contenttweaker.api.methods.MethodParameters;

@FunctionalInterface
public interface IIntegerMethod {
    Integer call(MethodParameters methodParameters);
}
