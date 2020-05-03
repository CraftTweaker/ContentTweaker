package com.blamejared.contenttweaker.blocks.types.custom;


import com.blamejared.contenttweaker.*;
import com.blamejared.contenttweaker.api.*;
import com.blamejared.contenttweaker.blocks.*;
import com.blamejared.contenttweaker.wrappers.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.impl.util.*;
import net.minecraft.block.*;
import net.minecraft.state.*;
import org.openzen.zencode.java.*;

import java.util.*;

@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.block.custom.BuilderBlockCustom")
public class BuilderBlockCustom implements IIsBuilder {
    
    private final BlockBuilder builder;
    private final Map<MCBlockStateProperty, String> blockStatePropertyMap;
    private final Set<MCBlockStateProperty> blockStatePropertiesForBlockStateJson;
    private BlockStateToModelMapping blockStateToModelMapping;
    private FunctionResourceLocationToIData modelNameToModelContentMapping;
    private PlaceStateMapping blockPlaceStateMapper;
    
    public BuilderBlockCustom(BlockBuilder builder) {
        this.builder = builder;
        this.blockStatePropertyMap = new HashMap<>();
        this.blockStatePropertiesForBlockStateJson = new HashSet<>();
    }
    
    public BlockBuilder getBuilder() {
        return builder;
    }
    
    public PlaceStateMapping getBlockPlaceStateMapper() {
        return blockPlaceStateMapper;
    }
    
    public BlockStateToModelMapping getBlockStateToModelMapping() {
        return blockStateToModelMapping;
    }
    
    public FunctionResourceLocationToIData getModelNameToModelContentMapping() {
        return modelNameToModelContentMapping;
    }
    
    public Map<MCBlockStateProperty, String> getBlockStatePropertyMap() {
        return blockStatePropertyMap;
    }
    
    public Set<MCBlockStateProperty> getBlockStatePropertiesForBlockStateJson() {
        return blockStatePropertiesForBlockStateJson;
    }
    
    @ZenCodeType.Method
    public BuilderBlockCustom withBlockStateToModelMapping(BlockStateToModelMapping blockStateToModelMapping) {
        this.blockStateToModelMapping = blockStateToModelMapping;
        return this;
    }
    
    @ZenCodeType.Method
    public BuilderBlockCustom withModelNameToModelContentMapping(FunctionResourceLocationToIData modelNameToModelContentMapping) {
        this.modelNameToModelContentMapping = modelNameToModelContentMapping;
        return this;
    }
    
    @ZenCodeType.Method
    public BuilderBlockCustom withBlockPlaceStateMapper(PlaceStateMapping blockPlaceStateMapper) {
        this.blockPlaceStateMapper = blockPlaceStateMapper;
        return this;
    }
    
    @ZenCodeType.Method
    public BuilderBlockCustom withBlockStateProperty(MCBlockStateProperty property, String defaultValue) {
        return withBlockStateProperty(property, defaultValue, true);
    }
    
    @ZenCodeType.Method
    public BuilderBlockCustom withBlockStateProperty(MCBlockStateProperty property, String defaultValue, boolean usedInBlockStateJson) {
        this.blockStatePropertyMap.put(property, defaultValue);
        if(usedInBlockStateJson) {
            this.blockStatePropertiesForBlockStateJson.add(property);
        }
        return this;
    }
    
    @Override
    public void build(MCResourceLocation location) {
        final CoTBlockCustom block = new CoTBlockCustom(this, location.getInternal()) {
            @Override
            protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
                super.fillStateContainer(builder);
                for(MCBlockStateProperty mcBlockStateProperty : blockStatePropertyMap.keySet()) {
                    builder.add(mcBlockStateProperty.getInternal());
                }
                
            }
        };
        VanillaFactory.registerBlock(block);
    }
}
