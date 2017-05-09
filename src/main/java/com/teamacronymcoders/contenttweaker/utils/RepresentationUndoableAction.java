package com.teamacronymcoders.contenttweaker.utils;

import com.teamacronymcoders.contenttweaker.api.IRepresentation;
import minetweaker.IUndoableAction;

public class RepresentationUndoableAction implements IUndoableAction {
    private IRepresentation representation;

    public RepresentationUndoableAction(IRepresentation representation) {
        this.representation = representation;
    }

    @Override
    public void apply() {
        representation.register();
    }

    @Override
    public boolean canUndo() {
        return false;
    }

    @Override
    public void undo() {

    }

    @Override
    public String describe() {
        return "Adding Content: " + representation.getName() + " with Type: " + representation.getTypeName();
    }

    @Override
    public String describeUndo() {
        return null;
    }

    @Override
    public Object getOverrideKey() {
        return null;
    }
}
