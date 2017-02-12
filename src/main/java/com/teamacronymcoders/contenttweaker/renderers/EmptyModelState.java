package com.teamacronymcoders.contenttweaker.renderers;

import com.google.common.base.Optional;
import net.minecraftforge.common.model.IModelPart;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

public class EmptyModelState implements IModelState {
    @Override
    public Optional<TRSRTransformation> apply(Optional<? extends IModelPart> part) {
        return Optional.absent();
    }
}
