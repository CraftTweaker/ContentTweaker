package com.blamejared.contenttweaker.fluids;

import com.blamejared.contenttweaker.ContentTweaker;
import com.blamejared.contenttweaker.api.items.IIsCotItem;
import com.blamejared.contenttweaker.api.resources.ResourceType;
import com.blamejared.contenttweaker.api.resources.WriteableResource;
import com.blamejared.contenttweaker.api.resources.WriteableResourceTemplate;
import com.blamejared.contenttweaker.color.IItemHasColor;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Supplier;

public class CoTFluidBucketItem extends BucketItem implements IIsCotItem, IItemHasColor {
    private final int color;

    public CoTFluidBucketItem(Supplier<? extends Fluid> supplier, Properties builder, int color) {
        super(supplier, builder);
        this.color = color;
    }

    @Nonnull
    @Override
    public Collection<WriteableResource> getResourcePackResources() {
        ResourceLocation location = getRegistryNameNonNull();
        return Collections.singleton(new WriteableResourceTemplate(ResourceType.ASSETS, location, "models", "item").withTemplate(ResourceType.ASSETS, new ResourceLocation(ContentTweaker.MOD_ID, "models/item/item_bucket")));
    }

    @Nonnull
    @Override
    public Collection<WriteableResource> getDataPackResources() {
        return Collections.emptyList();
    }

    @Override
    public int getColor(ItemStack stack, int tintIndex) {
        return tintIndex == 1 ? color : -1;
    }
}
