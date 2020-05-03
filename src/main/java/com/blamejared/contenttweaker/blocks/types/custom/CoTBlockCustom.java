package com.blamejared.contenttweaker.blocks.types.custom;

import com.blamejared.contenttweaker.api.blocks.*;
import com.blamejared.contenttweaker.api.items.*;
import com.blamejared.contenttweaker.api.resources.*;
import com.blamejared.contenttweaker.blocks.*;
import com.blamejared.contenttweaker.wrappers.*;
import com.blamejared.crafttweaker.impl.blocks.*;
import mcp.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.state.*;
import net.minecraft.util.*;

import javax.annotation.*;
import java.util.*;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
class CoTBlockCustom extends Block implements IIsCoTBlock {
    
    private final IIsCotItem item;
    private final BuilderBlockCustom builder;
    
    @SuppressWarnings("unchecked,rawtypes")
    public CoTBlockCustom(BuilderBlockCustom builder, ResourceLocation location) {
        super(builder.getBuilder().getBlockProperties());
        this.setRegistryName(location);
        this.item = new CoTBlockItem(this, builder.getBuilder().getItemProperties());
        this.builder = builder;
        
        BlockState defaultState = getDefaultState();
        for(Map.Entry<MCBlockStateProperty, String> entry : builder.getBlockStatePropertyMap()
                .entrySet()) {
            
            
            final IProperty internal = entry.getKey().getInternal();
            final Optional optional = internal.parseValue(entry.getValue());
            
            if(optional.isPresent()) {
                defaultState = defaultState.with(internal, (Comparable) optional.get());
            }
        }
        
        setDefaultState(defaultState);
    }
    
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        final PlaceStateMapping blockPlaceStateMapper = builder.getBlockPlaceStateMapper();
        if(blockPlaceStateMapper == null) {
            return super.getStateForPlacement(context);
        }
        
        return blockPlaceStateMapper.getState(new MCBlockState(getDefaultState()), new MCBlockItemUseContext(context))
                .getInternal();
    }
    
    public BuilderBlockCustom getBuilder() {
        return builder;
    }
    
    @Nonnull
    @Override
    public IIsCotItem getItem() {
        return item;
    }
    
    @Nonnull
    @Override
    public Collection<WriteableResource> getResourcePackResources() {
        final Collection<WriteableResource> out = new HashSet<>();
        final WriteableResourceBlockStateCustom blockState = new WriteableResourceBlockStateCustom(this);
        out.add(blockState);
        blockState.getReferencedModelsAndTextures().forEach(out::add);
        
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
