package com.blamejared.contenttweaker.fluids;

import com.blamejared.contenttweaker.ContentTweaker;
import com.blamejared.contenttweaker.api.fluids.IIsCotFluid;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import javax.annotation.Nullable;
import java.util.Objects;

final class CoTFlowingFluid implements IIsCotFluid {
    private ForgeFlowingFluid.Flowing fluid;
    private final ResourceLocation resourceLocation;
    private final boolean isMolten;
    private final boolean tagged;

    public CoTFlowingFluid(ResourceLocation resourceLocation, boolean isMolten, boolean tagged) {
        this.resourceLocation = new ResourceLocation(ContentTweaker.MOD_ID, resourceLocation.getPath() + "_flowing");
        this.isMolten = isMolten;
        this.tagged = tagged;
    }

    @Override
    public ResourceLocation getRegistryName() {
        return resourceLocation;
    }

    @Override
    public FlowingFluid get() {
        return Objects.requireNonNull(fluid);
    }

    @Nullable
    @Override
    public FlowingFluidBlock getFluidBlock() {
        return null;
    }

    @Override
    public void updateFluid(FlowingFluid fluid) {
        this.fluid = (ForgeFlowingFluid.Flowing) fluid;
    }

    @Override
    public boolean isMolten() {
        return isMolten;
    }

    @Override
    public boolean isTagged() {
        return tagged;
    }
}
