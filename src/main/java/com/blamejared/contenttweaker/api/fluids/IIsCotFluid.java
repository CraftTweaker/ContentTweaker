package com.blamejared.contenttweaker.api.fluids;

import com.blamejared.contenttweaker.api.IHasResourceLocation;
import com.blamejared.contenttweaker.api.IHasResourcesToWrite;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.FlowingFluid;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nullable;
import java.util.function.Supplier;

@ZenRegister
@Document("mods/contenttweaker/API/fluid/IIsCotFluid")
@ZenCodeType.Name("mods.contenttweaker.fluid.IIsCotFluid")
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
}
