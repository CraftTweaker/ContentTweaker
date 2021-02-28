package com.blamejared.contenttweaker.fluids;

import com.blamejared.contenttweaker.ContentTweaker;
import com.blamejared.contenttweaker.api.items.IIsCotItem;
import com.blamejared.contenttweaker.api.resources.ResourceType;
import com.blamejared.contenttweaker.api.resources.WriteableResource;
import com.blamejared.contenttweaker.api.resources.WriteableResourceTemplate;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Supplier;

public class CoTFluidBucketItem extends BucketItem implements IIsCotItem {
    public CoTFluidBucketItem(Supplier<? extends Fluid> supplier, Properties builder) {
        super(supplier, builder);
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
    public boolean allowTinted() {
        return true;
    }

    @Override
    public void setAllowTinted() {
        throw new UnsupportedOperationException();
    }
}
