package com.blamejared.contenttweaker.fluids;

import com.blamejared.contenttweaker.api.fluids.IIsCotFluid;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import java.util.Objects;

final class CoTStillFluid implements IIsCotFluid {
    private ForgeFlowingFluid.Source fluid;
    private FlowingFluidBlock fluidBlock;
    private final ResourceLocation location;
    private final boolean isMolten;

    public CoTStillFluid(ResourceLocation location, boolean isMolten) {
        this.location = location;
        this.isMolten = isMolten;
    }

    @Override
    public ResourceLocation getRegistryName() {
        return location;
    }

    @Override
    public FlowingFluid get() {
        return Objects.requireNonNull(fluid);
    }

    @Override
    public FlowingFluidBlock getFluidBlock() {
        return Objects.requireNonNull(fluidBlock);
    }

    @Override
    public void updateFluid(FlowingFluid fluid) {
        this.fluid = (ForgeFlowingFluid.Source) fluid;
    }

    @Override
    public void setFluidBlock(FlowingFluidBlock fluidBlock) {
        this.fluidBlock = fluidBlock;
    }

    @Override
    public boolean isMolten() {
        return isMolten;
    }
}
