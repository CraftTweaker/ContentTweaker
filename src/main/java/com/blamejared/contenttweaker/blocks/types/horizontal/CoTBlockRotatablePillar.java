package com.blamejared.contenttweaker.blocks.types.horizontal;

import com.blamejared.contenttweaker.*;
import com.blamejared.contenttweaker.api.blocks.*;
import com.blamejared.contenttweaker.api.items.*;
import com.blamejared.contenttweaker.api.resources.*;
import com.blamejared.contenttweaker.blocks.*;
import net.minecraft.block.*;
import net.minecraft.util.*;

import javax.annotation.*;
import java.util.*;

final class CoTBlockRotatablePillar extends RotatedPillarBlock implements IIsCoTBlock {
    
    private final IIsCotItem item;
    private final ResourceLocation end;
    private final ResourceLocation sides;
    
    public CoTBlockRotatablePillar(BlockBuilderPillarRotatable blockBuilderPillarRotatable, ResourceLocation location) {
        super(blockBuilderPillarRotatable.getBlockBuilder().getBlockProperties());
        this.setRegistryName(location);
        item = new CoTBlockItem(this, blockBuilderPillarRotatable.getBlockBuilder().getItemProperties());
        end = blockBuilderPillarRotatable.getEnd(location);
        sides = blockBuilderPillarRotatable.getSides(location);
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
        out.add(WriteableResourceImage.noImage(ImageType.BLOCK, end));
        if(!end.equals(sides)) {
            out.add(WriteableResourceImage.noImage(ImageType.BLOCK, sides));
        }
        
        final WriteableResourceTemplate blockModelTemplate = new WriteableResourceTemplate(ResourceType.ASSETS, location, "models", "block").withTemplate(ResourceType.ASSETS, new ResourceLocation(ContentTweaker.MOD_ID, "models/block/block_rotatable_pillar")).setLocationProperty(end, "END").setLocationProperty(sides, "SIDE");
        out.add(blockModelTemplate);
        
        final WriteableResourceTemplate blockStateTemplate = new WriteableResourceTemplate(ResourceType.ASSETS, location, "blockstates").withTemplate(ResourceType.ASSETS, new ResourceLocation(ContentTweaker.MOD_ID, "blockstates/block_rotatable_pillar")).setLocationProperty(location);
        out.add(blockStateTemplate);
        return out;
    }
    
    @Nonnull
    @Override
    public Collection<WriteableResource> getDataPackResources() {
        return Collections.singleton(new WriteableResourceLootTableItem(getRegistryName()));
    }
}
