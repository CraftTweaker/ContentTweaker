package com.blamejared.contenttweaker.blocks.types.machine;

import com.blamejared.contenttweaker.*;
import com.blamejared.contenttweaker.api.blocks.*;
import com.blamejared.contenttweaker.blocks.*;
import com.blamejared.contenttweaker.blocks.types.machine.capability.*;
import com.blamejared.crafttweaker.api.*;
import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.impl.util.*;
import org.openzen.zencode.java.*;

import java.util.*;
import java.util.function.*;

@ZenRegister
@ZenCodeType.Name("mods.contenttweaker.block.machine.BuilderMachine")
public class BuilderMachine extends BlockTypeBuilder {
    
    private final Map<ICotCapability, ICoTCapabilityConfiguration> capabilities;
    
    public BuilderMachine(BlockBuilder blockBuilder) {
        super(blockBuilder);
        this.capabilities = new HashMap<>();
    }
    
    
    public Map<ICotCapability, ICoTCapabilityConfiguration> getCapabilities() {
        return capabilities;
    }
    
    @ZenCodeType.Method
    public <T extends IIsCoTCapabilityBuilder> BuilderMachine buildCapability(Class<T> typeofT, Function<T, T> fun) {
        try {
            final T instance = typeofT.newInstance();
            final T apply = fun.apply(instance);
            apply.applyToBuilder(this);
        } catch(InstantiationException | IllegalAccessException e) {
            CraftTweakerAPI.logThrowing("Error applying capability: ", e);
        }
        return this;
    }
    
    
    
    @Override
    public void build(MCResourceLocation location) {
        final CoTBlockTile coTBlockTile = new CoTBlockTile(this, location);
        VanillaFactory.registerBlock(coTBlockTile);
    }
    
    public boolean hasCapability(ICotCapability capability) {
        return capabilities.containsKey(capability);
    }
    
    public void addCapability(ICotCapability capability, ICoTCapabilityConfiguration configuration){
        this.capabilities.put(capability, configuration);
    }
}
