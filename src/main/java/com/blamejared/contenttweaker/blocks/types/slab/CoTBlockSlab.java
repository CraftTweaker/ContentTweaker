package com.blamejared.contenttweaker.blocks.types.slab;

import com.blamejared.contenttweaker.*;
import com.blamejared.contenttweaker.api.blocks.*;
import com.blamejared.contenttweaker.api.items.*;
import com.blamejared.contenttweaker.api.resources.*;
import com.blamejared.contenttweaker.blocks.*;
import net.minecraft.block.*;
import net.minecraft.util.*;

import javax.annotation.*;
import java.util.*;

public class CoTBlockSlab extends SlabBlock implements IIsCoTBlock {
    
    private final IIsCotItem item;
    private final ResourceLocation sides;
    private final ResourceLocation top;
    private final ResourceLocation bottom;
    
    public CoTBlockSlab(BlockBuilderSlab blockBuilderSlab, ResourceLocation location) {
        super(blockBuilderSlab.getBlockBuilder().getBlockProperties());
        this.setRegistryName(location);
        
        this.item = new CoTBlockItem(this, blockBuilderSlab.getBlockBuilder().getItemProperties());
        this.top = blockBuilderSlab.getTop(location);
        this.sides = blockBuilderSlab.getSides(location);
        this.bottom = blockBuilderSlab.getBottom(location);
    }
    
    @Nonnull
    @Override
    public IIsCotItem getItem() {
        return item;
    }
    
    @Nonnull
    @Override
    public Collection<WriteableResource> getResourcePackResources() {
        final ResourceLocation location = getRegistryNameNonNull();
        
        final Collection<WriteableResource> out = new ArrayList<>();
        out.add(WriteableResourceImage.noImage(ImageType.BLOCK, top));
        out.add(WriteableResourceImage.noImage(ImageType.BLOCK, bottom));
        out.add(WriteableResourceImage.noImage(ImageType.BLOCK, sides));
        
        
        final WriteableResourceTemplate blockStateTemplate = new WriteableResourceTemplate(ResourceType.ASSETS, location, "blockstates").withTemplate(ResourceType.ASSETS, new ResourceLocation(ContentTweaker.MOD_ID, "blockstates/block_slab")).setLocationProperty(location);
        out.add(blockStateTemplate);
        
        final WriteableResourceTemplate modelBottomTemplate = new WriteableResourceTemplate(ResourceType.ASSETS, location, "models", "block").withTemplate(ResourceType.ASSETS, new ResourceLocation(ContentTweaker.MOD_ID, "models/block/block_slab")).setLocationProperty(top, "TOP").setLocationProperty(bottom, "BOTTOM").setLocationProperty(sides, "SIDE").setProperty("SLAB_PARENT", "slab");
        out.add(modelBottomTemplate);
        
        final WriteableResourceTemplate modelTopTemplate = new WriteableResourceTemplate(ResourceType.ASSETS, location.getNamespace(), location.getPath() + "_top", "models", "block").withTemplate(ResourceType.ASSETS, new ResourceLocation(ContentTweaker.MOD_ID, "models/block/block_slab")).setLocationProperty(top, "TOP").setLocationProperty(bottom, "BOTTOM").setLocationProperty(sides, "SIDE").setProperty("SLAB_PARENT", "slab_top");
        out.add(modelTopTemplate);
        
        final WriteableResourceTemplate modelDoubleTemplate = new WriteableResourceTemplate(ResourceType.ASSETS, location.getNamespace(), location.getPath() + "_double", "models", "block").withTemplate(ResourceType.ASSETS, new ResourceLocation(ContentTweaker.MOD_ID, "models/block/block_slab")).setLocationProperty(top, "TOP").setLocationProperty(bottom, "BOTTOM").setLocationProperty(sides, "SIDE").setProperty("SLAB_PARENT", "cube_bottom_top");
        out.add(modelDoubleTemplate);
        
        return out;
    }
    
    @Nonnull
    @Override
    public Collection<WriteableResource> getDataPackResources() {
        return Collections.singleton(new WriteableResourceLootTableItem(getRegistryName(), "slab"));
    }
}
