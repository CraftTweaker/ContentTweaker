package com.teamacronymcoders.contenttweaker.api.methods.callables;

import com.teamacronymcoders.contenttweaker.api.methods.MethodParameters;

@FunctionalInterface
public interface ICallableMethod {
    void callMethod(MethodParameters parameters);
}
