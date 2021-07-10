package com.blamejared.contenttweaker.api.fluids;

import com.blamejared.contenttweaker.api.IHasResourceLocation;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.FlowingFluid;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public interface IIsCotFluid extends IHasResourceLocation, Supplier<FlowingFluid> {
    /**
     * Updates the vanilla fluid it handles.
     */
    void updateFluid(FlowingFluid fluid);

    /**
     * Gets block of the fluid, returns null if the fluid is flowing type
     */
    @Nullable
    FlowingFluidBlock getFluidBlock();

    /**
     * Sets block of the fluid, shouldn't be overridden if the fluid is flowing type
     */
    default void setFluidBlock(FlowingFluidBlock flowingFluidBlock) {}

    /**
     * @return if the fluid is molten
     */
    boolean isMolten();
}
