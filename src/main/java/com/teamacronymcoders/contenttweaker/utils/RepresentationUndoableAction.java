package com.teamacronymcoders.contenttweaker.utils;

import com.teamacronymcoders.contenttweaker.api.IRepresentation;
import crafttweaker.IAction;

public class RepresentationUndoableAction implements IAction {
    private IRepresentation representation;

    public RepresentationUndoableAction(IRepresentation representation) {
        this.representation = representation;
    }

    @Override
    public void apply() {
        representation.register();
    }

    @Override
    public String describe() {
        return "Adding Content: " + representation.getName() + " with Type: " + representation.getTypeName();
    }
}
