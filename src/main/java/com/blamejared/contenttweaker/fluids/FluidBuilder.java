package com.blamejared.contenttweaker.fluids;

import com.blamejared.contenttweaker.ContentTweaker;
import com.blamejared.contenttweaker.VanillaFactory;
import com.blamejared.contenttweaker.api.IIsBuilder;
import com.blamejared.contenttweaker.api.fluids.IIsCotFluid;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import org.openzen.zencode.java.ZenCodeType;

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
    private boolean tagged = true;
    private final int color;

    /**
     * Creates a new FluidBuilder with default colorized textures
     * @param isMolten if the fluid is molten
     * @param color the color of the fluid
     *
     * @docParam isMolten true
     * @docParam color 0x66ccff
     */
    @ZenCodeType.Constructor
    public FluidBuilder(boolean isMolten, int color) {
        this(isMolten, color, isMolten ? MOLTEN_STILL_TEXTURE : LIQUID_STILL_TEXTURE, isMolten ? MOLTEN_FLOW_TEXTURE : LIQUID_FLOW_TEXTURE);
        builder.color(color);
    }

    /**
     * Creates a new FluidBuilder with two textures
     * @param isMolten if the fluid is molten
     * @param color the bucket fluid color
     * @param stillTexture the texture resource location of still fluid block
     * @param flowTexture the texture resource location of flowing fluid block
     *
     * @docParam isMolten true
     * @docParam color 0x66ccff
     * @docParam stillTexture <resource:contenttweaker:fluid/liquid>
     * @docParam flowTexture <resource:contenttweaker:fluid/liquid_flowing>
     */
    @ZenCodeType.Constructor
    public FluidBuilder(boolean isMolten, int color, ResourceLocation stillTexture, ResourceLocation flowTexture) {
        this.builder = FluidAttributes.builder(stillTexture, flowTexture);
        this.isMolten = isMolten;
        this.color = color;
        builder.sound(isMolten ? SoundEvents.ITEM_BUCKET_FILL_LAVA : SoundEvents.ITEM_BUCKET_FILL, isMolten ? SoundEvents.ITEM_BUCKET_EMPTY_LAVA : SoundEvents.ITEM_BUCKET_EMPTY);
    }

    /**
     * The light-level emitted by the fluid
     *
     * default value is 0
     *
     * @docParam luminosity 15
     */
    @ZenCodeType.Method
    public FluidBuilder luminosity(int luminosity) {
        builder.luminosity(luminosity);
        return this;
    }

    /**
     * How fast you can walk in the fluid?
     *
     * default value is 1000
     *
     * @docParam density 1400
     */
    @ZenCodeType.Method
    public FluidBuilder density(int density) {
        builder.density(density);
        return this;
    }

    /**
     * The Fluid's temperature
     *
     * default value is 300
     *
     * @docParam temperature 500
     */
    @ZenCodeType.Method
    public FluidBuilder temperature(int temperature) {
        builder.temperature(temperature);
        return this;
    }

    /**
     * How quickly the fluid spreads
     *
     * default value is 1000
     *
     * @docParam viscosity 800
     */
    @ZenCodeType.Method
    public FluidBuilder viscosity(int viscosity) {
        builder.viscosity(viscosity);
        return this;
    }

    /**
     * Is the fluid gaseous (flows upwards instead of downwards)?
     */
    @ZenCodeType.Method
    public FluidBuilder gaseous() {
        builder.gaseous();
        return this;
    }

    /**
     * By default, the fluid will be tagged as `<tags:fluids:minecraft:water>` or `<tags:fluids:minecraft:lava>` automatically.
     *
     * If you don't want to tag this fluid, call the method to deny it.
     */
    @ZenCodeType.Method
    public FluidBuilder notTagged() {
        this.tagged = false;
        return this;
    }

    @Override
    public void build(ResourceLocation location) {
        String path = location.getPath();
        IIsCotFluid stillFluid = new CoTStillFluid(location, isMolten, tagged);
        IIsCotFluid flowingFluid = new CoTFlowingFluid(location, isMolten, tagged);

        ForgeFlowingFluid.Properties properties = new ForgeFlowingFluid.Properties(stillFluid, flowingFluid, builder);
        AbstractBlock.Properties fluidBlockProperties = AbstractBlock.Properties.create(isMolten ? Material.LAVA : Material.WATER)
                .doesNotBlockMovement()
                .hardnessAndResistance(100.0f)
                .noDrops();
        FlowingFluidBlock fluidBlock = new FlowingFluidBlock(stillFluid, fluidBlockProperties);
        CoTFluidBucketItem bucketItem = new CoTFluidBucketItem(stillFluid, new Item.Properties().maxStackSize(1).containerItem(Items.BUCKET).group(ItemGroup.MISC), color);

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
