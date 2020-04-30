package com.blamejared.contenttweaker.blocks;

import com.blamejared.contenttweaker.blocks.functions.*;
import com.blamejared.contenttweaker.blocks.wrappers.*;
import com.blamejared.crafttweaker.impl.blocks.*;
import com.blamejared.crafttweaker.impl.util.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.state.*;

import javax.annotation.*;
import java.util.*;

@ParametersAreNonnullByDefault
public class CoTBlock extends Block {
    
    
    private final MCBlockProperties properties;
    
    public CoTBlock(MCBlockProperties properties) {
        super(properties.getInternal());
        this.properties = properties;
    
        BlockState defaultState = getDefaultState();
        for(Map.Entry<MCBlockStateProperty, String> entry : properties.getBlockStatePropertyMap()
                .entrySet()) {
            final IProperty internal = entry.getKey().getInternal();
            final Optional optional = internal.parseValue(entry.getValue());
        
            if(optional.isPresent()) {
                //noinspection rawtypes
                defaultState = defaultState.with(internal, (Comparable) optional.get());
            }
        }
        
        setDefaultState(defaultState);
    }
    
    public MCBlockProperties getProperties() {
        return properties;
    }
    
    public MCResourceLocation getMCResourceLocation() {
        return new MCResourceLocation(getRegistryName());
    }
    
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        final PlaceStateMapping placeStateMapping = properties.placeStateMapping;
        if(placeStateMapping == null) {
            return super.getStateForPlacement(context);
        }
        final MCBlockItemUseContext mcContext = new MCBlockItemUseContext(context);
        final MCBlockState state = placeStateMapping.getState(new MCBlockState(getDefaultState()), mcContext);
        return state == null ? null : state.getInternal();
    }
}
