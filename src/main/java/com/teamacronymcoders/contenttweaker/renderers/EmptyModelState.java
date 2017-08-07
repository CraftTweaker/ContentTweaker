package com.teamacronymcoders.contenttweaker.renderers;

import net.minecraftforge.common.model.IModelPart;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

import java.util.Optional;

public class EmptyModelState implements IModelState {
    @Override
    public Optional<TRSRTransformation> apply(Optional<? extends IModelPart> part) {
        return Optional.empty();
    }
}
