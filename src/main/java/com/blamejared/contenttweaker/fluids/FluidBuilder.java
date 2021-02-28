package com.blamejared.contenttweaker.fluids;

import com.blamejared.contenttweaker.ContentTweaker;
import com.blamejared.contenttweaker.VanillaFactory;
import com.blamejared.contenttweaker.api.IIsBuilder;
import com.blamejared.contenttweaker.api.fluids.IIsCotFluid;
import com.blamejared.contenttweaker.api.resources.WriteableResource;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.fluid.FluidBuilder")
@Document("mods/contenttweaker/API/fluid/FluidBuilder")
public class FluidBuilder implements IIsBuilder {
    private static final ResourceLocation LIQUID_STILL_TEXTURE = new ResourceLocation(ContentTweaker.MOD_ID, "fluid/liquid");
    private static final ResourceLocation LIQUID_FLOW_TEXTURE = new ResourceLocation(ContentTweaker.MOD_ID, "fluid/liquid_flow");
    private static final ResourceLocation MOLTEN_STILL_TEXTURE = new ResourceLocation(ContentTweaker.MOD_ID, "fluid/molten");
    private static final ResourceLocation MOLTEN_FLOW_TEXTURE = new ResourceLocation(ContentTweaker.MOD_ID, "fluid/molten_flowing");

    private final FluidAttributes.Builder builder;
    private final boolean isMolten;
    private final int color;

    /**
     * Creates a new FluidBuilder with default colorized textures
     * @param isMolten if the fluid is molten
     * @param color the color of the fluid
     */
    @ZenCodeType.Constructor
    public FluidBuilder(boolean isMolten, int color) {
        this.builder = FluidAttributes.builder(isMolten ? MOLTEN_STILL_TEXTURE : LIQUID_STILL_TEXTURE, isMolten ? MOLTEN_FLOW_TEXTURE : LIQUID_FLOW_TEXTURE);
        builder.color(color);
        this.isMolten = isMolten;
        this.color = color;
    }

    /**
     * Creates a new FluidBuilder with two textures
     * @param isMolten if the fluid is molten
     * @param color the bucket fluid color
     * @param stillTexture the texture resource location of still fluid block
     * @param flowTexture the texture resource location of flowing fluid block
     */
    @ZenCodeType.Constructor
    public FluidBuilder(boolean isMolten, int color, ResourceLocation stillTexture, ResourceLocation flowTexture) {
        this.builder = FluidAttributes.builder(stillTexture, flowTexture);
        this.isMolten = isMolten;
        this.color = color;
    }

    @Override
    public void build(ResourceLocation location) {
        String path = location.getPath();
        IIsCotFluid stillFluid = new IIsCotFluid() {
            private ForgeFlowingFluid.Source fluid;
            private FlowingFluidBlock fluidBlock;

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
        };
        IIsCotFluid flowingFluid = new IIsCotFluid() {
            private ForgeFlowingFluid.Flowing fluid;

            @Override
            public ResourceLocation getRegistryName() {
                return new ResourceLocation(ContentTweaker.MOD_ID, path + "_flowing");
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
        };
        ForgeFlowingFluid.Properties properties = new ForgeFlowingFluid.Properties(stillFluid, flowingFluid, builder);
        AbstractBlock.Properties fluidBlockProperties = AbstractBlock.Properties.create(isMolten ? Material.WATER : Material.LAVA)
                .doesNotBlockMovement()
                .hardnessAndResistance(100.0f)
                .noDrops();
        FlowingFluidBlock fluidBlock = new FlowingFluidBlock(stillFluid, fluidBlockProperties);
        CoTFluidBucketItem bucketItem = new CoTFluidBucketItem(stillFluid, new Item.Properties().maxStackSize(1).containerItem(Items.BUCKET).group(ItemGroup.MISC));
        bucketItem.setRegistryName(path + "_bucket");
        fluidBlock.setRegistryName(stillFluid.getRegistryNameNonNull());
        properties.block(() -> fluidBlock);
        properties.bucket(() -> bucketItem);
        stillFluid.setFluidBlock(fluidBlock);
        flowingFluid.updateFluid((FlowingFluid) new ForgeFlowingFluid.Flowing(properties).setRegistryName(flowingFluid.getRegistryNameNonNull()));
        stillFluid.updateFluid((FlowingFluid) new ForgeFlowingFluid.Source(properties).setRegistryName(stillFluid.getRegistryNameNonNull()));
        VanillaFactory.queueFluidForRegistration(stillFluid);
        VanillaFactory.queueFluidForRegistration(flowingFluid);
        VanillaFactory.queueItemForRegistration(bucketItem);
    }
}
