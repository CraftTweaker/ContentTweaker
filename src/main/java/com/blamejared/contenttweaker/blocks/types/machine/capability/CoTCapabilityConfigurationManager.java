package com.blamejared.contenttweaker.blocks.types.machine.capability;

import com.blamejared.contenttweaker.blocks.types.machine.gui.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.text.*;
import net.minecraftforge.api.distmarker.*;

import java.util.*;

public class CoTCapabilityConfigurationManager {
    
    private final Map<ICotCapability, ICoTCapabilityConfiguration> configurationMap;
    
    public CoTCapabilityConfigurationManager() {
        this.configurationMap = new HashMap<>();
    }
    
    public boolean hasCapability(ICotCapability capability) {
        return configurationMap.containsKey(capability);
    }
    
    public void addCapability(ICotCapability capability, ICoTCapabilityConfiguration configuration) {
        configurationMap.put(capability, configuration);
    }
    
    @OnlyIn(Dist.CLIENT)
    public CoTScreen createScreen(CoTContainer container, PlayerInventory playerInventory, ITextComponent textComponent) {
        return new CoTScreen(container, playerInventory, textComponent);
    }
    
    public void createInstancesFor(CoTCapabilityInstanceManager instanceManager) {
        for(Map.Entry<ICotCapability, ICoTCapabilityConfiguration> entry : configurationMap.entrySet()) {
            final ICotCapability capability = entry.getKey();
            final ICoTCapabilityConfiguration configuration = entry.getValue();
            final ICotCapabilityInstance capabilityInstance = configuration.createCapabilityInstance(instanceManager);
            instanceManager.addInstance(capability, capabilityInstance);
        }
    }
}
