package com.blamejared.contenttweaker.blocks.types.stairs;

import com.blamejared.contenttweaker.api.blocks.*;
import com.blamejared.contenttweaker.api.items.*;
import com.blamejared.contenttweaker.api.resources.*;
import com.blamejared.contenttweaker.blocks.*;
import com.blamejared.crafttweaker.impl.util.*;
import net.minecraft.block.*;

import javax.annotation.*;
import java.util.*;

final class CoTStairsBlock extends StairsBlock implements IIsCoTBlock {
    
    private final IIsCotItem item;
    private final MCResourceLocation top, bottom, sides;
    
    public CoTStairsBlock(BuilderStairs builderStairs, MCResourceLocation location) {
        super(Blocks.AIR::getDefaultState, builderStairs.getBlockBuilder().getBlockProperties());
        this.setRegistryName(location.getInternal());
        this.item = new CoTBlockItem(this, builderStairs.getBlockBuilder().getItemProperties());
        this.top = builderStairs.getTop(location);
        this.bottom = builderStairs.getBottom(location);
        this.sides = builderStairs.getSides(location);
    }
    
    @Nonnull
    @Override
    public IIsCotItem getItem() {
        return item;
    }
    
    @Nonnull
    @Override
    public Collection<WriteableResource> getResourcePackResources() {
        final MCResourceLocation location = getMCResourceLocation();
        final Collection<WriteableResource> out = new ArrayList<>();
        for(MCResourceLocation texture : new HashSet<>(Arrays.asList(top, bottom, sides))) {
            out.add(new WriteableResourceImage(ImageType.BLOCK, texture));
        }
        
        out.add(new WriteableResourceBlockStateStairs(location));
        out.add(new WriteableResourceModelStairs(location, WriteableResourceModelStairs.ModelType.BASE, top, bottom, sides));
        out.add(new WriteableResourceModelStairs(location, WriteableResourceModelStairs.ModelType.INNER, top, bottom, sides));
        out.add(new WriteableResourceModelStairs(location, WriteableResourceModelStairs.ModelType.OUTER, top, bottom, sides));
        
        return out;
    }
    
    @Nonnull
    @Override
    public Collection<WriteableResource> getDataPackResources() {
        final Collection<WriteableResource> out = new ArrayList<>();
        out.add(new WriteableResourceLootTableItem(getMCResourceLocation()));
        return out;
    }
}
